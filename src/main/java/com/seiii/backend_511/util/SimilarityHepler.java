package com.seiii.backend_511.util;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.seiii.backend_511.po.report.Report;
import com.seiii.backend_511.po.report.ReportSimilar;
import com.seiii.backend_511.vo.report.ReportSimilarVO;
import com.seiii.backend_511.vo.report.ReportVO;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.*;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;



public class SimilarityHepler {


    private JiebaSegmenter segmenter = new JiebaSegmenter();
    private String indexPath="file/index/";
    private int fileCount=0;

    public List<ReportSimilar> findSimilarity(ReportVO report, List<Report> base) throws IOException {
        String text=report.getDescription();
        String content=text.replaceAll("[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]", "");
        content=content.replaceAll("\\d+(?:[.,]\\d+)*\\s*", "");
        content = content.replaceAll("\\t|\\r|\\n","");
        content = content.replaceAll(" ","");

        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> strings = segmenter.sentenceProcess(content);

        index(base);
        HashMap<String,Float> textTfIdfMap=getTextTfIdf(strings);
        HashMap<Report,Map<String,Float>> allTfIdfMap=getAllTfIdf(base);
        HashMap<Report,Double> cosSimMap=cosineSimilarity(textTfIdfMap,allTfIdfMap);

        List<Map.Entry<Report,Double>> list=new ArrayList<Map.Entry<Report, Double>>(cosSimMap.entrySet());
        Iterator<Map.Entry<Report,Double>> iterator=list.iterator();
        while (iterator.hasNext()){
            Map.Entry<Report,Double> nowElement=iterator.next();
            if(nowElement.getKey().getUserId()==report.getUserId()){
                iterator.remove();
            }
        }
        list.sort(new Comparator<Map.Entry<Report, Double>>() {
            @Override
            public int compare(Map.Entry<Report, Double> o1, Map.Entry<Report, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        for(Map.Entry<Report,Double> e:list){
            if(e.getKey().getUserId()==report.getUserId()){
                 list.remove(e);
            }
        }

        File indexDir=new File(indexPath);
        for(File f:indexDir.listFiles()){
            f.delete();
        }

        List<ReportSimilar> res=new LinkedList<>();
        int size=Math.min(5,list.size());
        for(int i=0;i<size;i++){
            if(list.get(i).getValue().isNaN()||list.get(i).getValue()<0.01){
                continue;
            }else {
                ReportSimilar reportSimilar=new ReportSimilar(list.get(i).getKey(),list.get(i).getValue());
                res.add(reportSimilar);
            }
        }
        return res;
    }

    public void index(List<Report> base) throws IOException {
        NIOFSDirectory dir = new NIOFSDirectory(new File(indexPath));
        IndexWriter indexWriter = new IndexWriter(dir,
                new IndexWriterConfig(Version.LUCENE_36, new WhitespaceAnalyzer(Version.LUCENE_36)));
        indexWriter.deleteAll();
        indexWriter.forceMergeDeletes();
        for(Report r:base){
            String content=r.getDescription();
            content=content.replaceAll("\\d+(?:[.,]\\d+)*\\s*", "");
            content=content.replaceAll("[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]", "");
            content = content.replaceAll("\\t|\\r|\\n","");
            content = content.replaceAll(" ","");
            List<String> vecList=segmenter.sentenceProcess(content);
            StringBuilder sb=new StringBuilder();
            for(String s:vecList){
                sb.append(s+" ");
            }
            content=sb.toString();
            if(content.length()>0){
                content=content.substring(0,content.length()-1);
                Document doc = new Document();
                doc.add(new Field("docContent", new StringReader(content), Field.TermVector.YES));
                doc.add(new Field("docID", new StringReader(String.valueOf(r.getId())), Field.TermVector.YES));
                fileCount++;
                indexWriter.addDocument(doc);
            }
        }
        indexWriter.close();
    }

    public HashMap<Report,Map<String, Float>> getAllTfIdf(List<Report> base) throws IOException {
        HashMap<Report, Map<String, Float>> scoreMap = new HashMap<Report, Map<String, Float>>();

        IndexReader re = IndexReader.open(NIOFSDirectory.open(new File(indexPath)), true);

        for(int i=0;i<fileCount;i++){
            Map<String, Float> wordMap = new HashMap<String, Float>();

            TermFreqVector termsFreq = re.getTermFreqVector(i, "docContent");
            TermFreqVector termsFreqDocId = re.getTermFreqVector(i, "docID");

            String docID = termsFreqDocId.getTerms()[0];
            Report resReport=null;
            for(int j=0;j<base.size();j++){
                if(base.get(j).getId().equals(Integer.parseInt(docID))){
                    resReport=base.get(j);
                    break;
                }
            }
            int[] freq = termsFreq.getTermFrequencies();
            String[] terms = termsFreq.getTerms();
            int noOfTerms = terms.length;
            DefaultSimilarity simi = new DefaultSimilarity();
            for (int j = 0; j < noOfTerms; j++)
            {
                int noOfDocsContainTerm = re.docFreq(new Term("docContent", terms[j]));
                float tf = simi.tf(freq[j]);
                float idf = simi.idf(noOfDocsContainTerm,fileCount);
                wordMap.put(terms[j], (tf * idf));
            }
            scoreMap.put(resReport, wordMap);
        }
        re.close();
        return scoreMap;
    }

    public HashMap<String,Float> getTextTfIdf(List<String> termList) throws IOException {
        Map<String,Integer> termFreqMap = new HashMap<String,Integer>();
        for (String term : termList)
        {
            if (termFreqMap.get(term) == null)
            {
                termFreqMap.put(term,1);
                continue;
            }

            termFreqMap.put(term,termFreqMap.get(term) + 1);
        }

        HashMap<String, Float> scoreMap = new HashMap<String, Float>();

        IndexReader re = IndexReader.open(NIOFSDirectory.open(new File(indexPath)), true);
        DefaultSimilarity simi = new DefaultSimilarity();

        for (String term : termList)
        {
            int noOfDocsContainTerm = re.docFreq(new Term("docContent", term));
            float tf = simi.tf(termFreqMap.get(term));
            float idf = simi.idf(noOfDocsContainTerm, fileCount);
            scoreMap.put(term, (tf * idf));
        }
        re.close();
        return scoreMap;
    }

    public HashMap<Report,Double> cosineSimilarity(Map<String, Float> searchTextTfIdfMap,HashMap<Report, Map<String, Float>> allTfIdfMap)
    {
        //key是相似的文档名称，value是与当前文档的相似度
        HashMap<Report,Double> similarityMap = new HashMap<Report,Double>();

        //计算查找文本向量绝对值
        double searchValue = 0;
        for (Map.Entry<String, Float> entry : searchTextTfIdfMap.entrySet())
        {
            searchValue += entry.getValue() * entry.getValue();
        }

        for (Map.Entry<Report, Map<String, Float>> docEntry : allTfIdfMap.entrySet())
        {
            Report report=docEntry.getKey();
            Map<String, Float> docScoreMap = docEntry.getValue();

            double termValue = 0;
            double acrossValue = 0;
            for (Map.Entry<String, Float> termEntry : docScoreMap.entrySet())
            {
                if (searchTextTfIdfMap.get(termEntry.getKey()) != null)
                {
                    acrossValue += termEntry.getValue() * searchTextTfIdfMap.get(termEntry.getKey());
                }

                termValue += termEntry.getValue() * termEntry.getValue();
            }

            Double res=acrossValue/(Math.sqrt(termValue) * Math.sqrt(searchValue));
            if(res>1){
                res=1.0;
            }
            similarityMap.put(report,res);
        }

        return similarityMap;
    }
}
