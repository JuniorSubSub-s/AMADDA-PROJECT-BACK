package amadda_back.amadda_back.calendarpage.openapi.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import amadda_back.amadda_back.calendarpage.openapi.domain.HistoryDaysDTO;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Service
public class HistoryService {

    // public List<HistoryDaysDTO> parseXml(String xmlData) {
    //     List<HistoryDaysDTO> list = null;
    //     try {
    //         // JAXBContext 생성
    //         JAXBContext jaxbContext = JAXBContext.newInstance(HistoryDaysDTOList.class);
    //         System.out.println("service JAXVcontext");
            
    //         // Unmarshaller 생성
    //         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    //         System.out.println("service Unmarshaller");
            
    //         // XML 데이터를 Java 객체로 변환
    //         StringReader reader = new StringReader(xmlData);
    //         HistoryDaysDTOList historyDaysDTOList = (HistoryDaysDTOList) unmarshaller.unmarshal(reader);
            
    //         // 리스트 반환
    //         list = historyDaysDTOList.getItems();
    //     } catch (JAXBException e) {
    //         e.printStackTrace();
    //     }
    //     return list;
    // }

    public List<HistoryDaysDTO> parseXml(String xmlData) {

        List<HistoryDaysDTO> list = new ArrayList<>();
    
        try {
            // DocumentBuilderFactory와 DocumentBuilder를 사용하여 XML 데이터를 파싱할 준비
            DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
            DocumentBuilder        builder  = factory.newDocumentBuilder();
            Document               document = builder.parse(new InputSource(new StringReader(xmlData))); // XML 문자열을 InputSource로 변환하여 파싱할 준비
    
            // XML 문서에서 "item" 태그를 가진 모든 요소를 찾아 NodeList로 반환
            NodeList itemListNodes = document.getElementsByTagName("item");
            System.out.println("service itemlist node " + itemListNodes.getLength());
    
            // itemListNodes.getLength()가 0일 경우 처리하지 않음
            if (itemListNodes.getLength() > 0) {
                for (int i = 0; i < itemListNodes.getLength(); i++) {
                    Node itemListNode = itemListNodes.item(i);
    
                    if (itemListNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element itemListElement = (Element) itemListNode;
    
                        HistoryDaysDTO startBusStop;
    
                        String dateName = getElementValue(itemListElement, "dateName");
                        String locdate = getElementValue(itemListElement, "locdate");
    
                        startBusStop = HistoryDaysDTO.builder()
                                                    .dateName(dateName)
                                                    .locdate(locdate)
                                                    .build();
    
                        list.add(startBusStop);
                    }
                }
            } else {
                System.out.println("No 'item' elements found in XML data.");
            }
    
        } catch (Exception e) {
            // 예외 처리 로직 (필요한 경우 추가)
            e.printStackTrace();
        }
    
        return list;
    }
    

    // 특정 태그(예: "dateName" 또는 "locdate")의 텍스트 값을 추출하는 유틸리티 메서드
    // 주어진 tagName을 가진 노드를 찾고, 그 노드의 텍스트 내용을 반환합니다. 만약 해당 태그가 없으면 null을 반환
    private String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

}

