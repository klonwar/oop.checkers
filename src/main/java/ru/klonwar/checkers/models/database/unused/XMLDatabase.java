package ru.klonwar.checkers.models.database.unused;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.klonwar.checkers.models.database.CheckersDatabase;
import ru.klonwar.checkers.models.database.GameInfo;
import ru.klonwar.checkers.models.database.QueryResponse;
import ru.klonwar.checkers.models.database.User;
import ru.klonwar.checkers.models.game.Game;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XMLDatabase implements CheckersDatabase {
    private final String xmlLink;

    public XMLDatabase() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream("./app.properties"));
            xmlLink = p.get("xml-link").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public XMLDatabase(String xmlLink) {
        this.xmlLink = xmlLink;
    }

    @Override
    public QueryResponse addUser(User user) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlLink);
            Element root = document.getDocumentElement();

            Node users = root.getElementsByTagName("Users").item(0);

            String lastId = "";
            NodeList nl = document.getElementsByTagName("user");
            if (nl.getLength() != 0) {
                Node lastItem = nl.item(nl.getLength() - 1);
                Node id = lastItem.getChildNodes().item(0);
                lastId = id.getTextContent();
            } else {
                lastId = "0";
            }

            Element newUser = document.createElement("user");
            Element id = document.createElement("id");
            id.appendChild(document.createTextNode("" + (Integer.parseInt(lastId) + 1)));
            newUser.appendChild(id);

            Element login = document.createElement("login");
            login.appendChild(document.createTextNode("" + user.getLogin()));
            newUser.appendChild(login);

            Element password = document.createElement("password");
            password.appendChild(document.createTextNode("" + user.getPassword()));
            newUser.appendChild(password);

            users.appendChild(newUser);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(xmlLink);
            transformer.transform(source, result);

            return new QueryResponse(true, "Пользователь успешно зарегистрирован");
        } catch (Exception e) {
            return new QueryResponse(false, e.getMessage());
        }
    }

    @Override
    public QueryResponse addGame(Game game) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlLink);
            Element root = document.getDocumentElement();

            Node users = root.getElementsByTagName("Games").item(0);

            String lastId = "";
            NodeList nl = document.getElementsByTagName("game");
            if (nl.getLength() != 0) {
                Node lastItem = nl.item(nl.getLength() - 1);
                Node id = lastItem.getChildNodes().item(0);
                lastId = id.getTextContent();
            } else {
                lastId = "0";
            }

            Element newUser = document.createElement("game");
            Element id = document.createElement("id");
            id.appendChild(document.createTextNode("" + (Integer.parseInt(lastId) + 1)));
            newUser.appendChild(id);

            Element wpi = document.createElement("white_player_id");
            wpi.appendChild(document.createTextNode("" + game.getWhitePlayer().getId()));
            newUser.appendChild(wpi);

            Element bpi = document.createElement("black_player_id");
            bpi.appendChild(document.createTextNode("" + game.getBlackPlayer().getId()));
            newUser.appendChild(bpi);

            Element winner = document.createElement("winner");
            winner.appendChild(document.createTextNode("" + game.getWinner()));
            newUser.appendChild(winner);

            Element ft = document.createElement("finish_time");
            ft.appendChild(document.createTextNode("" + game.getFinishTime()));
            newUser.appendChild(ft);

            users.appendChild(newUser);

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(xmlLink);
            transformer.transform(source, result);

            return new QueryResponse(true);
        } catch (Exception e) {
            return new QueryResponse(false);
        }
    }

    @Override
    public List<GameInfo> getGamesInfoForUserID(int userID) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlLink);
            Element root = document.getDocumentElement();

            Node users = root.getElementsByTagName("Games").item(0);

            List<GameInfo> lgi = new ArrayList<>();
            String lastId = "";
            NodeList nl = document.getElementsByTagName("game");
            for (int i = 0; i < nl.getLength(); i++) {
                Node item = nl.item(i);
                NodeList attrs = item.getChildNodes();

                if (attrs.item(1).getTextContent().equals(userID + "") || attrs.item(2).getTextContent().equals(userID + "")) {
                    lgi.add(new GameInfo(
                            Integer.parseInt(attrs.item(0).getTextContent()),
                            Integer.parseInt(attrs.item(1).getTextContent()),
                            Integer.parseInt(attrs.item(2).getTextContent()),
                            Integer.parseInt(attrs.item(3).getTextContent()),
                            Long.parseLong(attrs.item(4).getTextContent())
                    ));
                }
            }

            return lgi;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlLink);
            Element root = document.getDocumentElement();

            NodeList nl = document.getElementsByTagName("user");
            for (int i = 0; i < nl.getLength(); i++) {
                Node item = nl.item(i);
                NodeList attrs = item.getChildNodes();

                if (attrs.item(1).getTextContent().equals(login + "") && attrs.item(2).getTextContent().equals(password + "") ) {
                    return new User(
                            Integer.parseInt(attrs.item(0).getTextContent()),
                            attrs.item(1).getTextContent(),
                            attrs.item(2).getTextContent()
                    );
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
