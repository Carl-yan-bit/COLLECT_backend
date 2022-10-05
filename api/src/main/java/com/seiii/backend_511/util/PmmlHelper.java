package com.seiii.backend_511.util;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.jpmml.model.PMMLUtil;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PmmlHelper {
    private Evaluator evaluator;
    public PmmlHelper(String path){
        PMML pmml;
        File file = new File(path);
        try{
            InputStream inputStream = new FileInputStream(file);
            pmml = PMMLUtil.unmarshal(inputStream);
            //创建模型
            ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
            evaluator = modelEvaluatorFactory.newModelEvaluator(pmml);
        }catch (FileNotFoundException f){
            f.printStackTrace();
            System.err.println("文件不存在");
        } catch (JAXBException | SAXException e) {
            e.printStackTrace();
            System.err.println("创建pmml模型时出现错误");
        }
    }
    public Map<FieldName,?> predict(Map<String,Integer> integerMap){
        List<InputField> inputFields = evaluator.getInputFields();
        Map<FieldName,FieldValue> arguments = new HashMap<>();
        for(InputField inputField:inputFields){
            FieldName inputFieldName = inputField.getName();
            Integer realValue = integerMap.get(inputFieldName.getValue());
            FieldValue inputFieldValue = inputField.prepare(realValue);
            arguments.put(inputFieldName,inputFieldValue);
        }
        Map<FieldName,?> results = evaluator.evaluate(arguments);
        List<TargetField> targetFields = evaluator.getTargetFields();
        for (TargetField targetField : targetFields) {
            FieldName targetFieldName = targetField.getName();
            Object targetFieldValue = results.get(targetFieldName);
            System.out.println("target: " + targetFieldName.getValue());
            System.out.println(targetFieldValue);
        }
        return results;
    }
}
