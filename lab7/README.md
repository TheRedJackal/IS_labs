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

