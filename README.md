# patch-cxf-rt-bindings-soap
Patch för Apache CXF för att bibehålla bakåtkompabilitet med tidigare versioner för hantering av soapAction vid webservice-anrop.

Patchen är avsedd för Mule-3.7.0 och ska ge bakåtkompabilitet med Mule-3.3.1 vad gäller soapAction.
Se även: https://skl-tp.atlassian.net/browse/SKLTP-808

## Beskrivning: SoapAction i webservice-anrop
* Mule-3.7.0 kräver att HTTP-header "soapAction" ska vara satt till samma värde som i WSDL - eller inte vara satt alls, annars fås ett fel.

* Mule-3.3.1 verifierade inte detta, dvs släppte igenom sådana anrop.

* Detta beror på en säkerhetsuppdatering i underliggande webservice-ramverk Apache CXF (Mule-3.3.1: cxf-2.5.1, Mule-3.7.0: cxf-2.7.15) där spoofing av anrop med felaktig soapAction förhindras, ref: http://cxf.apache.org/cve-2012-3451.html

## Deployment av patch
1. Ladda ned patch från: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22se.skltp.patch.cxf.rtbindingssoap%22%20AND%20a%3A%22skltp-patch-cxf-rt-bindings-soap%22
2. Lägg skltp-patch-cxf-rt-bindings-soap-VERSION.jar i katalogen: mule-standalone-3.7.0/lib/user

## Testning
Testa med ett webservice-anrop (t ex till VP) som har en felaktig soapAction http-header satt.

Ska fungera efter patch, men inte innan för Mule-3.7.0.
