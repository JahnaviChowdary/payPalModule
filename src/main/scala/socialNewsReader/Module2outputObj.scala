package socialNewsReader

import org.json.JSONObject
import socialNewsReader.utils.enums.ConceptType
import socialNewsReader.utils.enums.ConceptType.ConceptType

class Module2outputObj {

  var conceptType: ConceptType = ConceptType.ENTITY
  var positionStart: Int = positionStart
  var positionEnd: Int = positionEnd

  def populateModule2output(input: JSONObject) = {
    if(input.getString("conceptType") == "ENTITY") {
      conceptType = ConceptType.ENTITY
    } else if(input.getString("conceptType") == "LINK") {
      conceptType = ConceptType.LINK
    } else {
      conceptType = ConceptType.TWITTER_USERNAME
    }

    positionStart = input.getInt("positionStart")
    positionEnd = input.getInt("positionEnd")
  }
}
