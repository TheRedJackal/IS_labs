# Lab 7

## Das Zertifikat beinhaltet zwei Identitäten, den Issuer und das Subject. Was bedeuten Issuer und Subject in einem Zertifikat ganz grundsätzlich bzw. was wird damit bezeichnet?

Issuer: Identität, welche das Zertifikat ausgestellt hat => Aussteller
Subject: Identität, welche mit dem im Subject public key feld verknüpft ist => Zertifikatsinhaber

##Issuer und Subject weisen in diesem Zertifikat den gleichen Wert auf. Wieso ist das so?

Weil wir als CA für uns ein eigenes Zertifikat erstellt haben.

## Das Zertifikat beinhaltet zwei lange Hex-Strings (jeweils mehrere Zeilen). Was beinhalten diese Strings?

Der erste ist der Public Key des Zertifikates, der zweite die Signatur

## Im Feld Signature Algorithm stehen die Namen von zwei verschiedenen kryptographischen Verfahren. Wozu dient dieses Feld und wieso stehen da zwei Verfahren drin?

Die erwähnten Verfahren werden für die Signierung des Zertifikats verwendet. SHA-256 zum hashen und RSA zum signieren.

## Wie beurteilen Sie die generelle kryptographische Sicherheit dieser beiden Verfahren (auch unter Berücksichtigung der Schlüssellänge)? Ist es in Ordnung, diese Verfahren heute noch zu verwenden?

Ja, ist nach wie vor der Standard. Zertifikate sind so oder so nicht so lange gültig, bis dahin wird dies also noch reichen.

## Befinden sich in diesem Zertifikats-Request irgendwelche kritischen Daten? D.h. ist es von zentraler Bedeutung, dass dieser Request nur über einen sicheren Kommunikationskanal zur CA gesendet wird? Können Sie sich ein Angriffsszenario vorstellen, wenn der Request ungesichert (d.h. weder verschlüsselt noch authentisiert / integritätsgeschützt) übertragen wird?

Nicht so tragisch. Im CSR befinden sich die Daten, welche dann sowieso im Zertifikat sichtbar sind (mail, organization, public key etc.). Es wird kein Private Key mitgeschickt.

## Wieso ist diese Signatur wichtig? Wieso genügt es nicht einfach, dass man den Public Key und das Subject zur CA sendet, um ein Zertifikat zu erhalten? Erklären Sie das am besten anhand eines Szenarios, das möglich wäre, wenn diese Signatur nicht verwendet würde.

Die Signatur garantiert, dass das es sich beim Vorliegenden Zertifikat um ein Vertrauenswürdiges Zertifikat handelt, und das die Angaben über die Website auch würklich Stimmen.

Ohne könnte ich beispielsweise meine Phishing Website mit einem Zertifikat ausstatten, das dem Nutzer angibt ich sei bspw. Microsoft.

## Betrachten Sie die Felder Issuer und Subject in beiden Zertifikaten. Inwiefern besteht hier eine Verbindung über die beiden Zertifikate hinweg? 

Anders als in der vorherigen Aufgabe ist hier das Subject unser Webserver. Dies bedeutet das Zertifikat für host.securitylab.local wurde von der CA Gruppe10_CA ausgestellt

## Wie kann damit generell festgestellt werden, ob ein Zertifikat potentiell von einem bestimmten CAZertifikat ausgestellt wurde? («Potentiell» deshalb, weil natürlich auch noch die Signatur auf dem ausgestellten Zertifikat korrekt sein muss.)

Aufgrund des Eintrages bei Issuer

## Im Zertifikat des Webservers steht bei Basic Constraints der Wert CA:FALSE. Offensichtlich ist dies also kein CA-Zertifikat. Das korrekte Setzen dieses Werts ist von höchster Wichtigkeit beim Ausstellen eines Zertifikats für einen Server. Wieso ist das so? Welches Angriffsszenario wäre möglich, wenn dieses Feld falsch (also auf CA:TRUE) gesetzt würde?

Das Verhalten wird in RFC 5280 beschrieben. Bei CA:True kann das Zertifikate zum signieren weiterer Zertifikate gebraucht werden.

## Weiter oben wurde gefragt, ob der Zertifikats-Request irgendwelche sicherheitskritischen Informationen beinhaltet. Wie sieht es nun mit dem ausgestellten Zertifikat aus? Befinden sich in diesem irgendwelche kritischen Daten? Oder anders gefragt, wäre es grundsätzlich in Ordnung, dass die CA das ausgestellte Zertifikat in einer ungesicherten E-Mail-Nachricht an den Antragssteller sendet?

Ja, denn die im Zertifikat enthaltenen Informationen werden dann später sowieso vom Server Publiziert.

## Betrachten Sie nochmals den ganzen Prozess um das Webserver-Zertifikat zu erhalten (Schlüsselpaar generieren, Zertifikats-Request generieren, Zertifikat ausstellen). Und nehmen Sie an, wir verwenden keine eigene CA, sondern eine offizielle (öffentliche) CA, die grundsätzliche Vorgehensweise sei aber gleich wie oben. Was geschieht während dieses ganzen Prozesses mit dem Private Key des Webservers? Verlässt dieser den Rechner des Antragsstellers irgendwann bzw. wird dieser an die CA gesendet?

Nein, dieser verlässt den PC des Antragsteller nie. Dies würde die Verwendung des Private Keys Sinnlos machen.

## Es erscheint ein Hinweis «dass die Verbindung nicht sicher ist» Was ist hier passiert? Ein Klick auf den Advanced-Button liefert weitere Informationen.

Der Browser kennt den Issuer also die CA nicht und weiss nicht, ob er ihr vertrauen kann.

## Welches Sicherheitsrisiko besteht, wenn Sie diese Warnung ignorieren und mit Add Exception... die Verbindung dennoch aufbauen würden (tun Sie das hier aber nicht)?

Mehrere, einerseits könnte es sich um eine Kopie der Seite handeln, die ich besuchen möchte, andererseits könnte es sich aber auch um eine Man-In-Middle Attacke handeln.

## Beeinflusst dies, wie sicher sich Ihre Mitarbeiter sein können, dass wenn diese z.B. https://www.google.ch kontaktieren und keine Zertifikatswarnung erscheint, sie auch wirklich direkt mit www.google.ch verbunden sind und niemand mithört?

Nein

## Nehmen Sie nun an, ein Mitarbeiter gelangt an den Private Key der CA. Was kann er damit nun tun?

Er könnte eigene Zertifikate erstellen die dann von Computern die das CA Zertifikat installiert haben, akzeptiert werden.

## Erstaunlicherweise erhalten Sie nun wieder eine Warnung, obwohl das CA-Zertifikat korrekt installiert ist und obwohl Sie mit dem richtigen Webserver kommunizieren. Was ist hier das Problem?

Das Zertifikat lautet auf den Hostnamen und nicht auf die IP Adresse, alternative Hostnamen würden ebenfalls nicht akzeptiert.

## Welcher Eintrag im Zertifikat wird vom Webbrowser verwendet, um festzustellen, zu welchem Webserver (bzw. zu welchem Hostnamen) das Zertifikat genau gehört? Seien Sie hier präzise: Wenn es sich nur um einen Teil eines der Felder im Zertifikat handelt, dann bezeichnen Sie dies genau.

Der Common Name (CN) des Subject Eintrages

## Im nächsten Schritt sucht der Browser das richtige CA-Zertifikat unter den vorinstallierten CAZertifikaten, d.h. das Zertifikat der CA, die das Webserver-Zertifikat ausgestellt hat. Welche Teilschritte werden dabei durchgeführt? Geben Sie hier insbesondere auch die Felder in den Zertifikaten ein, die verwendet werden, um das richtige CA-Zertifikat zu finden.
Es wird der Issuer, insbesondere der Common Name (cn) verwendet, da der Country (c) eintrag nur das Land und der Organisation (o) Eintrag nur die Austellende Instanz angibt.
Da die meisten Zertifikate nicht direkt vom Root signiert sind, sondern von einem Intermediate Zertifikat, wird dann dieser Schritt mit Intermediate Zertifkiat wiederholt, bis es in der Kette an ein Selbst-Signiertes Zertifikat ankommt.
Schlussendlich wird das letzte Zertifikat mit denjenigen Verglichen, die als Vertrauenswürdig hinterlegt sind.

## Ist das CA-Zertifikat gefunden so geht es im nächsten Schritt darum zu überprüfen, ob das Webserver-Zertifikat tatsächlich von der CA ausgestellt (signiert) wurde. Welche Teilschritte werden dabei durchgeführt? Gehen Sie hier insbesondere darauf ein, welche Schlüssel wozu verwendet werden.
Es wird geschaut ob aud dem Public Key und dem Algorithmus des übergeordneten Zertifikates die im Zertifikat vorhandene Signatur gebildet werden kann.
Bei intermediate Zertifikaten wird dieser Schritt mit dem Übergeordneten Zertfifikat wiederholt bis zum Root Zertifikat.

## Nun prüft der Browser, ob das Zertifikat zeitlich gültig ist und ob es wirklich dem Server gehört, mit dem der Browser kommunizieren möchte. Welche Teilschritte werden dabei durchgeführt?
Es wird geschaut ob sich das aktuelle Datum zwischen den beiden Daten im Feld Validity befinden. Um zu verifizieren das es sich um den richtigen Server handelt, wird der Common Name (cn) im feld Subject mit der Adresse im Browser verglichen.

## Starten sie danach den Webserver neu. Schliessen Sie ebenfalls den Webbrowser und starten Sie diesen erneut und versuchen Sie wiederum, eine TLS-Verbindung vom Browser zum Server aufzusetzen. Was beobachten Sie? Was ist passiert? Wieso funktioniert das nicht? Die Fehlermeldung erlaubt nicht wirklich einen klaren Rückschluss auf das dahinterliegende Problem; weitere Informationen erhalten Sie auch aus dem Error-Logfile von Apache (/var/log/apache2/error.log).
Der Client sendet kein Zertifikat mit.

## Welche TLS Version wird verwendet? Entspricht das einer sinnvollen Konfiguration?
TLSv1.2, ja, da TLSv1.3 erst dieses Jahr erschienen und nicht von allen Geräten unterstützt wird.

## Analysieren Sie die Liste der vom Client angebotenen Cipher Suites (in Client Hello). Wieviele Cipher Suites werden angeboten? Erkennen Sie hier Cipher Suites, die heute nicht mehr verwendet werden sollten?
Insgesamt werden 13 Cipher suites angezeigt, zwei davon (0xcca8 & 0xcca8) sind wireshark nicht bekannt.
Als schwach gelten gemäss ssllabs.com:
 * 0x2f - TLS_RSA_WITH_AES_128_CBC_SHA
 * 0x35 - TLS_RSA_WITH_AES_256_CBC_SHA
 * 0x0a - TLS_RSA_WITH_3DES_EDE_CBC_SHA

## Welche Cipher Suite wird vom Server ausgewählt (in Server Hello)? Anhand der Cipher Suite erkennen Sie die Art des Schlüsselaustausches – RSA oder Diffie-Hellman: Was wurde hier verwendet und wie beurteilen Sie diese Wahl bezüglich Perfect Forward Secrecy?
TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 (0xc02f)  
Es wurde Diffie-Hellman verwendet also PFS.

## Wie beweist der Webserver dem Browser im vorliegenden Fall, dass er den Private Key besitzt? Und in welcher Handshake-Nachricht befindet sich die zugehörige Information?
Am Server Hello angehängt ist der Server Key Exchange, darin werden die DH Parameter Gehasht und Signiert. 

## CertificateRequest (gehen Sie hier insb. auch auf den in dieser Nachricht mitgesendeten Distinguished Name ein):
Die angaben welche Art Zertifikat vom Client erwartet wird.
Der CN ist Gruppe10_ca besagt das ein vom CA Gruppe10_ca signiertes Zertifikat erwartet wird.
In Server Hello

## Certificate
Das Server Zertifikat damit der Client verifizieren kann, das es sich um den richtigen Server handelt. (Gemäss vorigen Fragen)
In Server Hello

## CertificateVerify
Der zur Verifizierung des Zertifikates generierte Hash sowie der verwendete Algorithmus

## Damit die komplette Chain geprüft werden kann muss der Browser alle drei Zertifikate kennen. Stellen Sie eine Vermutung auf, wie der Browser während dem TLS-Handshake zu allen drei Zertifikaten gelangt. Analysieren Sie den aufgezeichneten Handshake noch nicht.
Der Client kann anhand des Issuers das nächst höhere Zertifikat anfragen

## Analysieren Sie nun die Certificate-Nachricht des TLS-Handshake in Wireshark (wird vom Webserver zum Browser gesendet). Wieviele und welche Zertifikate sind dort erhalten? Das mittlere Fenster von Wireshark listet die Details im Zertifikat schön lesbar auf; damit können Sie die Zertifikate gut identifizieren.
Das Server Zertifikat sowie das übergeordnete Intermediate Zertifikat

## Basierend auf diesen neuen Erkenntnissen: Müssen Sie Ihre Antwort von vorhin («wie gelangt der Browser zu allen drei Zertifikaten») revidieren?
Ja

## Eine OCSP-Abfrage besteht aus einem Request und einer Response und wird über HTTP gesendet. Dazu muss der Browser zuerst einmal wissen, an wen er den OCSP/HTTP-Request richten sollte (d.h. den OCSP-Server, der oft auch als OCSP-Responder oder OCSP-Endpoint bezeichnet wird). Woher weiss das der Browser?
In den Extesnsions des Zertifikates gibt es den Punkt Authority Information Access darunter ist die URI zur OCSP hinterlegt

## Welches Feld ist das und welches Zertifikat in der Certificate-Chain wird damit identifiziert?
Das Feld serialNumber.
Zuerst das Intermediate und danach das Server Zertifikat

## Wie ist der Status des abgefragten Zertifikats?
certStatus£: good

## Die OCSP-Response ist signiert. Wieso ist das wichtig? Anders gefragt: Welche Attacke wäre möglich, wenn die Response nicht signiert wäre?
Ich könnte ein revoziertes oder selbst generiertes Zertifikat mit einer falschen OCSP URL versehen und einen Server machen der diese einfach akzeptiert.

## Die Seriennummer des abgefragten Zertifikats ist auch in der Response erhalten. Wieso ist das wichtig? Anders gefragt: Welche Attacke wäre möglich, wenn das nicht so wäre?
Ich habe sonst keine möglichkeit herauszufinden welches Zertifikat jetzt geprüft wurde. ich konnte die Anfrage abfangen und eine alte bestätigung zurück senden.

## Die Response enthält auch zwei Timestamps (thisUpdate und nextUpdate). Diese definieren das Zeitfenster, während dem die Antwort gültig ist. Wieso ist das wichtig? Anders gefragt: Welche Attacke wäre möglich, wenn kein Zeitfenster enthalten wäre?
Das Zertifikat könnte Revoziert werden und da der Browser das Zertifikat bereits überprüft hat, wäre es immer noch gültig

## Der Browser sollte also auch noch prüfen, ob das Intermediate-CA-Zertifikat noch gültig ist oder ob es von der CA revoziert wurde. Das kann man ebenfalls mit OCSP machen. Schauen Sie sich das Intermediate-CA-Zertifikat nochmals genauer an. Ist dort ein OCSP-Responder aufgeführt?
Ja

## Die CA ermöglicht es also auch, den Status des Intermediate-CA-Zertifikats zu überprüfen. Analysieren Sie als nächstes nochmals die aufgezeichneten Nachrichten in Wireshark. Können Sie hier eine OCSP-Abfrage bzgl. des Status dieses Zertifikats finden?
Ja

## Wie kann der Angreifer erreichen, dass das Zertifikat vom Browser akzeptiert wird?
In dem er den Zugriff zum OCSP Server verunmöglicht

## Studieren Sie die verschiedenen Extensions in der Nachricht. Welche davon instruiert den Server, dass der Browser OCSP-Stapling unterstützt?
status_request

## Wenn Sie die vom Server gesendeten Handshake-Nachrichten untersuchen, dann sollten Sie diese Nachricht einfach erkennen können. Wie lautet die Nachricht und was steckt in der Nachricht drin?
Certificate Status
Die OSCP Response

## Was schliessen Sie daraus? Hat der Server bei Ihrem Aufruf tatsächlich eine Anfrage an den OCSP-Responder gesendet?
Das Datum liegt in der Vergangenheit, also wurde wahrscheinlich keine neue Anfrage gesendet, da das nextUpdate Datum noch nicht überschritten wurde.

## Verifizieren Sie anhand der mit Wireshark aufgezeichneten weiteren Daten, ob Firefox auch noch selbst eine OCSP-Anfrage an den OCSP-Responder sendet. Finden Sie eine solche Anfrage? Und was schliessen Sie daraus?
Nein es gibt keinen weiteren Request. Firefox akzeptiert die OCSP Antwort des Servers

## Wie kann der Angreifer erreichen, dass das Zertifikat vom Browser akzeptiert wird? Welchen Vorteil bietet hier OCSP-Stapling im Vergleich zu der direkten Verwendung von OCSP?
Der Angreifer kann dank OCSP-stapling die alte noch nicht abgelaufene OCSP-Response senden. Bei einer Direkten Anfrage würde die CA mit Revoked antworten

## Betrachten Sie erneut die Man-in-the-Middle-Attacke von vorhin. Kann sie nach wie vor durchgeführt werden oder wird sie jetzt effektiv verhindert? Begründen Sie die Antwort!
Zwar ist kein Soft-Fail Angriff mehr möglich, jedoch ist die oben beschriebene Attacke immernoch möglich
