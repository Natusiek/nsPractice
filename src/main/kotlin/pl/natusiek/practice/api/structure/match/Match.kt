package pl.natusiek.practice.api.structure.match

interface Match {



    enum class MatchType { RANKED, UNRANKED }

    enum class MatchRound(val number: Int) { BO1(1), BO3(2), BO5(3) }

    enum class MatchSize(val number: Int) { SOLO(1), DUO(2), TRIO(3), QUINTET(5) }

}