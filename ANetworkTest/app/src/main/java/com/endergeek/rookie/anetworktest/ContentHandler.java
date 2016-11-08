package com.endergeek.rookie.anetworktest;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by wangsenhui on 11/8/16.
 *
 * SAX解析方式:用法复杂，语义清晰
 */
public class ContentHandler extends DefaultHandler{

    private static final String TAG = ContentHandler.class.getSimpleName();

    private String nodeName;

    private StringBuilder id;

    private StringBuilder name;

    private StringBuilder version;

    /**
     * A.开始XML解析时调用
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    /**
     * B.开始解析某个结点时调用
     * @param uri 命名空间URI，如果元素不存在命名空间URI或未处理命名空间则为空字符串
     * @param localName 本地名称(无前缀)，如果未处理命名空间则会空字符串
     * @param qName 限制名称(含前缀)，如果不可用则为空字符串
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // 记录当前结点名
        nodeName = localName;
        Log.d(TAG, "START-nodeName:" +nodeName);
    }

    /**
     * C.获取结点内容时调用
     * @param ch XML文档字符
     * @param start 数组起点
     * @param length 从数组中读取字符的数量
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 根据当前的结点名判断将内容添加到哪一个StringBuilder对象中
        if ("id".equals(nodeName)) {
            id.append(ch, start, length);
        } else if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("version".equals(nodeName)) {
            version.append(ch, start, length);
        }
    }

    /**
     * D.完成解析某个结点的时候调用
     * 叶结点处理完成依据:例如处理非叶结点时<app>与</app>对应，此时nodeName为上一完整步骤处理结尾值version，而localName为app，<apps>与</apps>同理
     * @param uri 命名空间URI，如果元素不存在命名空间URI或未处理命名空间则为空字符串
     * @param localName 本地名称(无前缀)，如果未处理命名空间则会空字符串
     * @param qName 限制名称(含前缀)，如果不可用则为空字符串
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Log.d(TAG, "END-nodeName:" +nodeName);
        Log.d(TAG, "END-localName:" +localName);
        if ("app".equals(localName)) {
            Log.d(TAG, "id is " + id.toString().trim()); // trim处理回车或换行符等
            Log.d(TAG, "name is " + name.toString().trim());
            Log.d(TAG, "version is " + version.toString().trim());
            // 最后需要清空StringBuilder
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    /**
     * E.完成XML解析的时候调用
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
    }
}
