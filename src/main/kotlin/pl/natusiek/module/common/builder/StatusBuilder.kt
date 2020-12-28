package pl.natusiek.module.common.builder

data class StatusBuilder(
    val status: Boolean
) {

    fun status(): String {
        return if (this.status) "&aWłączone" else "&cWyłączone"
    }

}
