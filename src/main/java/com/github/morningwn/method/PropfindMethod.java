package com.github.morningwn.method;

import com.github.morningwn.common.WebdavRequest;
import com.github.morningwn.common.WebdavResponse;
import com.github.morningwn.enums.HttpStatusCode;
import com.github.morningwn.enums.WebdavMethodType;
import com.github.morningwn.model.PropfindResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PropfindMethod implements WebdavMethod {

    @Override
    public WebdavResponse handleRequest(WebdavRequest request) {
        WebdavResponse response = new WebdavResponse();
        response.setHeaders(new HashMap<>());
        response.getHeaders().put("Content-Type", "application/xml; charset=utf-8");
        try {
            List<String> props = parseProps(request.getBody());
            List<PropfindResponseBody.ResourceResponse> resourceResponses = new ArrayList<>();
            // 这里只处理单个资源，实际可递归处理目录
            Path resourcePath = Paths.get(request.getPath());
            if (Files.exists(resourcePath)) {
                PropfindResponseBody.Prop prop = new PropfindResponseBody.Prop();
                if (props.isEmpty() || props.contains("displayname")) {
                    prop.setDisplayname(resourcePath.getFileName().toString());
                }
                if (props.isEmpty() || props.contains("getcontentlength")) {
                    prop.setGetcontentlength(Files.isDirectory(resourcePath) ? null : Files.size(resourcePath));
                }
                if (props.isEmpty() || props.contains("getlastmodified")) {
                    prop.setGetlastmodified(Files.getLastModifiedTime(resourcePath).toString());
                }
                PropfindResponseBody.ResourceResponse rr = new PropfindResponseBody.ResourceResponse();
                rr.setHref(request.getPath());
                rr.setProp(prop);
                rr.setStatus("HTTP/1.1 200 OK");
                resourceResponses.add(rr);
            }
            PropfindResponseBody responseBody = new PropfindResponseBody();
            responseBody.setResponses(resourceResponses);
            response.setStatusCode(HttpStatusCode.MULTI_STATUS);
            response.setBody(buildResponseXml(responseBody));
        } catch (Exception e) {
            response.setStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
            response.setBody("<error>" + e.getMessage() + "</error>");
        }
        return response;
    }

    private List<String> parseProps(String xml) {
        List<String> props = new ArrayList<>();
        if (xml == null || xml.trim().isEmpty()) {
            return props;
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            NodeList propNodes = doc.getElementsByTagName("prop");
            if (propNodes.getLength() > 0) {
                Element propElem = (Element) propNodes.item(0);
                NodeList children = propElem.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    if (children.item(i) instanceof Element) {
                        props.add(children.item(i).getNodeName());
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return props;
    }

    private String buildResponseXml(PropfindResponseBody responseBody) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        Element multistatus = doc.createElementNS("DAV:", "multistatus");
        doc.appendChild(multistatus);
        for (PropfindResponseBody.ResourceResponse rr : responseBody.getResponses()) {
            Element responseElem = doc.createElement("response");
            Element hrefElem = doc.createElement("href");
            hrefElem.setTextContent(rr.getHref());
            responseElem.appendChild(hrefElem);
            Element propstatElem = doc.createElement("propstat");
            Element propElem = doc.createElement("prop");
            PropfindResponseBody.Prop prop = rr.getProp();
            if (prop.getDisplayname() != null) {
                Element displaynameElem = doc.createElement("displayname");
                displaynameElem.setTextContent(prop.getDisplayname());
                propElem.appendChild(displaynameElem);
            }
            if (prop.getGetcontentlength() != null) {
                Element lengthElem = doc.createElement("getcontentlength");
                lengthElem.setTextContent(String.valueOf(prop.getGetcontentlength()));
                propElem.appendChild(lengthElem);
            }
            if (prop.getGetlastmodified() != null) {
                Element lastModElem = doc.createElement("getlastmodified");
                lastModElem.setTextContent(prop.getGetlastmodified());
                propElem.appendChild(lastModElem);
            }
            propstatElem.appendChild(propElem);
            Element statusElem = doc.createElement("status");
            statusElem.setTextContent(rr.getStatus());
            propstatElem.appendChild(statusElem);
            responseElem.appendChild(propstatElem);
            multistatus.appendChild(responseElem);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }

    @Override
    public WebdavMethodType getType() {
        return WebdavMethodType.PROPFIND;
    }
}
