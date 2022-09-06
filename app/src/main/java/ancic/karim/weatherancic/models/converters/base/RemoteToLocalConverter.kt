package ancic.karim.weatherancic.models.converters.base

interface RemoteToLocalConverter<REMOTE, LOCAL> {
    fun map(remote: REMOTE): LOCAL
}
