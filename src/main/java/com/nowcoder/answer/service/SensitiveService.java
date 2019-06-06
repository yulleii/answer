package com.nowcoder.answer.service;


import org.apache.commons.lang.CharUtils;
import com.nowcoder.answer.controller.HomeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {
    private static final Logger logger= LoggerFactory.getLogger(HomeController.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read =new InputStreamReader(is);
            BufferedReader bufferedReader=new BufferedReader(read);
            String lineTxt;
            while((lineTxt=bufferedReader.readLine())!=null){
                addWord(lineTxt.trim());
            }
            read.close();
        }catch(Exception e){
            logger.error("读取敏感词文件失败"+e.getMessage());
        }
    }
    //增加关键词
    private void addWord(String lineTxt){
        TreeNode tempNode=rootNode;
        for(int i=0;i<lineTxt.length();++i){
            Character c=lineTxt.charAt(i);
            if(isSymbol(c)){
                continue;
            }
            TreeNode node=tempNode.getSubNode(c);

            if(node==null){
                node=new TreeNode();
                tempNode.addSubNode(c,node);
            }

            tempNode=node;
            if(i==lineTxt.length()-1){
                tempNode.setkeywordEnd(true);
            }
        }
    }

    private class TreeNode{
        //是不是关键词的结尾
        private boolean end=false;
        //当前节点下所有的子节点
        private Map<Character,TreeNode>subNodes=new HashMap<>();
        public void addSubNode(Character key,TreeNode value){
            subNodes.put(key,value);
        }

        TreeNode getSubNode(Character key){
            return subNodes.get(key);
        }

        boolean isKeyWordEnd(){
            return end;
        }

        void setkeywordEnd(boolean end){
            this.end=end;
        }
    }
    private TreeNode rootNode=new TreeNode();

    private boolean isSymbol(char c){
        int ic=(int)c;
        //东亚文字
        return !CharUtils.isAsciiAlphanumeric(c) && (ic<0x2E80 || ic>0x9FFF);
    }

    public String filter(String text){
        if(StringUtils.isEmpty(text)){
            return text;
        }
        String replacement="***";
        StringBuilder sb=new StringBuilder();
        TreeNode tempNode=rootNode;
        int begin=0;
        int position=0;

        while(position<text.length()){
            char c=text.charAt(position);
            if(isSymbol(c)){
                if(tempNode==rootNode){
                    sb.append(c);
                    ++begin;
                }
                position++;
                continue;
            }
            tempNode=tempNode.getSubNode(c);
            if (tempNode==null){
                    sb.append(text.charAt(begin));
                    position=begin+1;
                    begin=position;
                    tempNode=rootNode;
            }else if(tempNode.isKeyWordEnd()){
                //发现敏感词
                sb.append(replacement);
                position=position+1;
                begin=position;
                tempNode=rootNode;
            }else{
                position=position+1;
            }

        }
        sb.append(text.substring(begin));
        return sb.toString();
    }

    public static void main(String[]argv){
        SensitiveService s=new SensitiveService();
        s.addWord("色情");
        s.addWord("赌博");
        System.out.println(s.filter("   我是色-情小孩子！  但是我不赌博"));
    }
}
