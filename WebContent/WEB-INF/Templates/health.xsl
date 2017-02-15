<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
 <InsertObservation service="SOS" version="1.0.1">
<AssignedSensorId>
urn:ogc:object:feature:Sensor:Patient:PatientID-@ID</AssignedSensorId>
<om:Observation>
<om:samplingTime>
<gml:TimeInstant>
<gml:timePosition>
Wed Jan 14 14:16:57 IST 2015</gml:timePosition>
</gml:TimeInstant>
</om:samplingTime>
<om:procedure xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:object:feature:Sensor:PatientID-@ID">
<om:observedProperty>
<swe:CompositePhenomenon dimension="1" xmlns:ns0="http://www.w3.org/1999/gml" ns0:id="cpid0">
<gml:name>
resultComponents</gml:name>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:systole"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:diastole"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:temperature"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:breathing_rate"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:GSRcond"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:GSRres"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:ecg"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:oxymeter"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:bpm"/>
<swe:component xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href="urn:ogc:def:phenomenon:OGC:1.0.30:body_position"/>
</swe:CompositePhenomenon>
</om:observedProperty>
<om:featureOfInterest>
<gml:FeatureCollection>
<gml:featureMember>
<sa:SamplingPoint xmlns:ns0="http://www.w3.org/1999/gml" ns0:id="PatientHealth">
<gml:name>
Patient Health</gml:name>
<sa:sampledFeature xmlns:ns0="http://www.w3.org/1999/xlink" ns0:href=" "/>
<sa:position>
<gml:Point>
<gml:pos srsName="urn:ogc:def:crs:EPSG:4326">
-147.33</gml:pos>
</gml:Point>
</sa:position>
</sa:SamplingPoint>
</gml:featureMember>
</gml:FeatureCollection>
</om:featureOfInterest>
<om:result>
<om:DataArray>
<swe:elementCount>
<swe:Count>
<swe:value>
1</swe:value>
</swe:Count>
</swe:elementCount>
<swe:elementType name="Components">
<swe:DataRecords>
<swe:field name="Time">
<swe:Time Definition="urn:ogc:data:time:iso8601"/>
</swe:field>
<swe:field name="feature">
<swe:Text definition="urn:ogc:data:time:iso8601"/>
</swe:field>
<swe:field name="Systole">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:systole">
<swe:uom code="*upper*"/>
</swe:Quantity>
</swe:field>
<swe:field name="Diastole">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:diastole">
<swe:uom code="*upper*"/>
</swe:Quantity>
</swe:field>
<swe:field name="Body Temperature">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:temperature">
<swe:uom code="*celc*"/>
</swe:Quantity>
</swe:field>
<swe:field name="Breathing Rate">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:breathing_rate">
<swe:uom code="*bpm*"/>
</swe:Quantity>
</swe:field>
<swe:field name="Galvinic Skin Response cond">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:GSRcond">
<swe:uom code="*micsiem*"/>
</swe:Quantity>
</swe:field>
<swe:field name="Galvinic Skin Response res">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:GSRres">
<swe:uom code="*micsiem*"/>
</swe:Quantity>
</swe:field>
<swe:field name="ElectroCardioGram">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:ecg">
<swe:uom code="*upper*"/>
</swe:Quantity>
</swe:field>
<swe:field name="Pulse">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:pulse_rate">
<swe:uom code="*hbps*"/>
</swe:Quantity>
</swe:field>
<swe:field name="Oxymeter">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:o2">
<swe:uom code="*hbps*"/>
</swe:Quantity>
</swe:field>
<swe:field name="BodyPosition">
<swe:Quantity definition="urn:ogc:def:phenomenon:OGC:1.0.30:body_position">
<swe:uom code="*mode*"/>
</swe:Quantity>
</swe:field>
</swe:DataRecords>
</swe:elementType>
<swe:encoding>
<swe:TextBlock blockSeparator=";" decimalSeparator="." tokenSeparator=","/>
</swe:encoding>
<swe:value>
@time,@sys,@dia,@temperature,@breathing_rate,@GSRcond, @GSRres,@ecg,@pulse_rate,@o2,@position</swe:value>
</om:DataArray>
</om:result>
</om:procedure>
</om:Observation>
</InsertObservation>
 
  
</xsl:template>

</xsl:stylesheet>