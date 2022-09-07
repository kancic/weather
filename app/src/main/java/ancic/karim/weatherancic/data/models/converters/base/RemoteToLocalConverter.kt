package ancic.karim.weatherancic.data.models.converters.base

interface RemoteToLocalConverter<REMOTE, LOCAL> {
    fun map(remote: REMOTE): LOCAL
}
