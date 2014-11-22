package org.sjsu.cmpe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.sjsu.cmpe.beans.ZillowPropertyDetailStage;
import org.sjsu.cmpe.dao.impl.ZillowPropertyDetailStageDAOImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SearchingManager {

    ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    ZillowPropertyDetailStageDAOImpl zillowDao = (ZillowPropertyDetailStageDAOImpl) context.getBean("zillowPropertyDao");

    public Entry<Float, Long> getBestResult(String key) {
        Map<Float, Long> resultMap = new TreeMap<Float, Long>().descendingMap();
        List<ZillowPropertyDetailStage> items = zillowDao.getAllData();
        float idf = getIdfValue(key);
        System.out.print("start iteration:" + items.size() + "\n");
        int count = 0;
        for (ZillowPropertyDetailStage item : items) {
            String description = item.getHomeDescription();
            float tfIdf = getTfValue(key, description) * idf;
            resultMap.put(tfIdf, item.getZpid());
            count++;
            System.out.print("item number:" + count + "\n");
        }

        for (Entry<Float, Long> entry : resultMap.entrySet()) {
            System.out.print("@@@@ tfidf: " + entry.getKey() + "  zpid: " + entry.getValue() + "\n");
        }

        return resultMap.entrySet().iterator().next();
    }

    private float getTfValue(String key, String text) {
        float tf = 0;
        Integer count = 0;
        try {
            text = text.toLowerCase().replace(".", "").replace(",", "").replace("!", "").replace("?", "").replace("&", "");
            String[] textArray = text.split(" ");
            int totalText = textArray.length;
            for (String word : textArray) {
                if (!DITCTIONARY.contains(word)) {
                    if (word.equals(key))
                        count++;
                }
            }
            tf = (float) count / totalText;
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        return tf;
    }

    private float getIdfValue(String key) {
        float idf = 0;
        int allItemNumber = zillowDao.getAllDataSize();
        int keyAppearCountNumber = 1 + zillowDao.getDescriptionRowSizeByKeyWord(key);
        idf = (float) allItemNumber / keyAppearCountNumber;
        return idf;
    }

    // May move to constant.java (if we have)
    public static final List<String> DITCTIONARY = new ArrayList<String>() {

        private static final long serialVersionUID = -8073767205202837435L;
        {
            add("and");
            add("with");
            add("of");
            add("is");
            add("are");
            add("on");
            add("in");
            add("it");
            add("there");
            add("the");
            add("be");
            add("to");
            add("a");
            add("I");
            add("that");
            add("for");
            add("you");
            add("this");
            add("he");
            add("they");
            add("but");
            add("we");
            add("from");
            add("by");
            add("she");
        }
    };
}
