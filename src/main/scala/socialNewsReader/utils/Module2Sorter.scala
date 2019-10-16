package socialNewsReader.utils

import java.util.Comparator

import socialNewsReader.Module2outputObj

object Module2Sorter extends Comparator[Module2outputObj]{

  override def compare(o1: Module2outputObj, o2: Module2outputObj) = {
    o1.positionStart.compareTo(o2.positionStart)
  }

}
