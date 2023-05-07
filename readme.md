# IOT-Ersatzaufgabe
## Ziel
Das Ziel dieses Projekts ist die Weiterentwicklung eines bestehenden Konsolen-Java-Programms, das in der Lage ist, Daten aus einem Solax Inverter mithilfe des Modbus-Protokolls zu lesen und zu interpretieren. Solax bietet eine PDF-Datei an, in der alle Input- und Holding-Register aufgeführt sind. Der Auftrag besteht darin, die in der PDF-Datei aufgeführten Register geeignet zu implementieren und ein Programm zu entwickeln, das die Holding- und Input-Register auf Grundlage der Solax-PDF-Datei liest, verarbeitet und darstellt.

## Funktionen
### Das Programm soll folgende Funktionen bieten:

Weiterentwicklung des bestehenden Konsolen-Java-Programms, um die in der Solax-PDF-Datei aufgeführten Register geeignet zu implementieren
Eine intuitive Benutzeroberfläche entwickeln, die Echtzeitdaten wie Stromertrag, Leistungsaufnahme, Abgabe und Überschuss anzeigt
Eine Datenbankanbindung implementieren, um die Daten für spätere Analysen zu speichern
Benutzerfreundliche Alarme und Benachrichtigungen generieren, um den Benutzer über Abweichungen von erwarteten Werten zu informieren.
Mindestanforderungen
Die Mindestanforderung für dieses Projekt ist die Entwicklung einer Java-Konsolenanwendung, die in der Lage ist, Daten aus einem Solax Inverter mithilfe des Modbus-Protokolls zu lesen und zu interpretieren, ohne dass eine GUI erforderlich ist. Die Konsolenanwendung sollte in der Lage sein, sowohl Holding- als auch Input-Register auf Basis der Solax-PDF-Datei zu verarbeiten und die Daten auf der Konsole auszugeben. Es sollte auch eine Möglichkeit geben, die Daten in einer Datenbank zu speichern und bei Bedarf Benachrichtigungen zu generieren, wenn bestimmte Schwellenwerte erreicht werden. Die Konsolenanwendung sollte benutzerfreundlich und gut dokumentiert sein, um sicherzustellen, dass sie leicht zu bedienen ist und dass der Code für zukünftige Entwickler leicht zu verstehen und zu warten ist.

## Verantwortlichkeiten
Das Projekt wird von einem Entwickler durchgeführt. Der Entwickler ist verantwortlich für die Entwicklung und Implementierung des Programms sowie für die Dokumentation und Testung der Software. Er wird eng mit dem Kunden zusammenarbeiten, um sicherzustellen, dass die Anforderungen vollständig verstanden und erfüllt werden. Der Kunde wird dem Entwickler Feedback geben und sicherstellen, dass das Programm seinen Anforderungen entspricht.

## usefull links

Hier sind einige nützliche Links für Modbus:

- Offizielle Modbus-Website: https://modbus.org/
- Modbus-Spezifikation: https://modbus.org/docs/Modbus_Application_Protocol_V1_1b3.pdf
- Modbus-Tutorial: https://www.automates.org/modbus-tutorial
- Modbus-Implementierung in Python: https://pymodbus.readthedocs.io/en/stable/
- Modbus-Implementierung in Java: https://github.com/infiniteautomation/modbus4j
- Modbus-TCP-Implementierung in Java: https://github.com/infiniteautomation/modbus4j/blob/master/examples/src/main/java/com/serotonin/modbus4j/example/tcp/TcpMasterTest.java
- Modbus-TCP-Implementierung in Python: https://github.com/riptideio/pymodbus/blob/master/examples/common/modbus_async_server.py