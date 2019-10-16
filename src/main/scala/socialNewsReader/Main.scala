package socialNewsReader

import java.util

import org.json.{JSONArray, JSONObject}
import socialNewsReader.utils.{Module2Sorter, TagConstants}
import socialNewsReader.utils.enums.ConceptType
import socialNewsReader.utils.enums.ConceptType.ConceptType

object Main {

  def main(args: Array[String]): Unit = {
    val module1output = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile"
    val module2output = new JSONArray()
    val obj1 = new JSONObject()
    obj1.put("conceptType", "ENTITY")
    obj1.put("positionStart", 14)
    obj1.put("positionEnd", 22)
    module2output.put(0, obj1)
    val obj2 = new JSONObject()
    obj2.put("conceptType", "ENTITY")
    obj2.put("positionStart", 0)
    obj2.put("positionEnd", 5)
    module2output.put(1, obj2)
    val obj3 = new JSONObject()
    obj3.put("conceptType", "TWITTER_USERNAME")
    obj3.put("positionStart", 55)
    obj3.put("positionEnd", 67)
    module2output.put(2, obj3)
    val obj4 = new JSONObject()
    obj4.put("conceptType", "LINK")
    obj4.put("positionStart", 37)
    obj4.put("positionEnd", 54)
    module2output.put(3, obj4)
    val res = getModule3output(module1output, module2output)
    println(res)

    //To extend my code with new types, appropriate new constants, enums and logic have to be added
  }

  def getModule3output(module1output: String, module2output: JSONArray): String = {
    var module3output: String = ""
    val module2outputList = new util.ArrayList[Module2outputObj]
    var curIndex = 0
    for(i <- 0 until module2output.length()) {
      val module2outputObj = new Module2outputObj()
      module2outputObj.populateModule2output(module2output.getJSONObject(i))
      val positionStart = module2outputObj.positionStart
      val positionEnd = module2outputObj.positionEnd
      module2outputList.add(i, module2outputObj)
    }
    module2outputList.sort(Module2Sorter)
    for(i <- 0 until module2outputList.size()) {
      val currObj = module2outputList.get(i)
      if(currObj.conceptType != ConceptType.TWITTER_USERNAME) {
        if (currObj.positionStart > curIndex) {
          module3output += module1output.substring(curIndex, currObj.positionStart)
        }
        val repStr = replaceString(module1output.substring(currObj.positionStart, currObj.positionEnd), currObj.conceptType)
        curIndex = currObj.positionEnd
        module3output += repStr
      }
      else {
        if (currObj.positionStart > curIndex) {
          module3output += module1output.substring(curIndex, currObj.positionStart+1)
        }
        val repStr = replaceString(module1output.substring(currObj.positionStart+1, currObj.positionEnd), currObj.conceptType)
        curIndex = currObj.positionEnd
        module3output += repStr
      }
    }
    if(curIndex != module1output.length+1) {
      module3output += module1output.substring(curIndex)
    }
    module3output
  }

  def replaceString(inputString: String, conceptType: ConceptType): String = {
    var outputString: String = ""
    if(conceptType == ConceptType.ENTITY) {
      outputString = TagConstants.ENTITY_START + inputString + TagConstants.ENTITY_END
    }
    else if(conceptType == ConceptType.LINK) {
      outputString = TagConstants.LINK_START + inputString + TagConstants.MID + inputString + TagConstants.LINK_END
    }
    else {
      outputString = TagConstants.TWITTER_USERNAME_START + inputString + TagConstants.MID + inputString + TagConstants.TWITTER_USERNAME_END
    }
    outputString
  }

}
