package pl.natusiek.module.saveeq.structure

import java.io.Serializable

class MemberEquipment : Serializable {

    val equipments: HashSet<Equipment> = hashSetOf()


}