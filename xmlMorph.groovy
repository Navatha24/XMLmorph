#!/usr/bin/env groovy

// xmlMorph variable XML conversion script
// by Oliver Erlewein
// 26/04/2018

@Grab('org.yaml:snakeyaml:1.18')
import org.yaml.snakeyaml.Yaml
import groovy.text.*
import groovy.xml.*

// 1st input parameter
if (args.size > 1) {
	// XML filter class
	def xmlConversionFile = args[0]

	// XML intut file
	def inFile = new File(args[1]).text
} else {
  println 'Please define arguments <conversion file> <input file> [<offset>]'
}

// Date offset if needed otherwise assumes current date
def dateOffset = (args.size() > 2) ? args[2].toInteger() : 0 

def xmlIn = new XmlParser().parseText(inFile)

// Import change class for XML conversion
converter = Eval.me("new ${xmlConversionFile}()")
converter.currentDate = (new Date() + dateOffset)
converter.dateOffset = dateOffset

// output result to stdout
println XmlUtil.serialize(converter.convertXML(xmlIn))
