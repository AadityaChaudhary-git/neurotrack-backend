package com.neurotrack.backend.controller;

import org.springframework.web.bind.annotation.*;
import weka.classifiers.Classifier;
import weka.core.*;

import java.util.ArrayList;
import java.util.Map;
import java.io.InputStream;

@RestController
@RequestMapping("/predict")
public class PredictionController {

    private Classifier model;

    public PredictionController() throws Exception {

        // 🔥 SAFE MODEL LOADING (IMPORTANT FOR DEPLOYMENT)
        InputStream modelStream = getClass().getClassLoader().getResourceAsStream("model.model");

        if (modelStream == null) {
            throw new RuntimeException("❌ Model file not found in resources!");
        }

        model = (Classifier) SerializationHelper.read(modelStream);

        System.out.println("✅ Model loaded successfully");
    }

    @PostMapping
    public Map<String, String> predict(@RequestBody InputData input) throws Exception {

        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("screen_time"));
        attributes.add(new Attribute("unlocks"));
        attributes.add(new Attribute("night_usage"));

        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("Low");
        classValues.add("Moderate");
        classValues.add("High");

        attributes.add(new Attribute("addiction_level", classValues));

        Instances dataset = new Instances("test", attributes, 0);
        dataset.setClassIndex(dataset.numAttributes() - 1);

        DenseInstance instance = new DenseInstance(dataset.numAttributes());

        // 🔥 REQUIRED
        instance.setDataset(dataset);

        instance.setValue(attributes.get(0), input.getScreen_time());
        instance.setValue(attributes.get(1), input.getUnlocks());
        instance.setValue(attributes.get(2), input.getNight_usage());

        dataset.add(instance);

        double result = model.classifyInstance(dataset.instance(0));

        String prediction = dataset.classAttribute().value((int) result);

        return Map.of("prediction", prediction);
    }
}