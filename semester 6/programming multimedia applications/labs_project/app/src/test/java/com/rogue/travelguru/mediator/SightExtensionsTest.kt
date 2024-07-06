import com.rogue.travelguru.data.local.SightEntity
import com.rogue.travelguru.data.mappers.toSight
import com.rogue.travelguru.data.mappers.toSightData
import com.rogue.travelguru.data.mappers.toSightEntity
import com.rogue.travelguru.data.mappers.toSightLocation
import com.rogue.travelguru.model.SightDataLocation
import com.rogue.travelguru.model.SightDataResource
import com.rogue.travelguru.model.SightPhoto
import org.junit.Test
import org.junit.Assert.*

class SightExtensionsTest {

    @Test
    fun testToSightLocation() {
        val sightDataLocation = SightDataLocation(1, "Location", 40.7128, -74.0060)
        val sightLocation = sightDataLocation.toSightLocation()
        assertEquals(1, sightLocation.id)
        assertEquals("Location", sightLocation.name)
        assertEquals(40.7128, sightLocation.latitude, 0.001)
        assertEquals(-74.0060, sightLocation.longitude, 0.001)
    }

    @Test
    fun testToSightData() {
        val sightDataResource = SightDataResource(1, "Resource", "Desc", "Long Desc", "videoUrl", "audioUrl", emptyList(), 40.7128, -74.0060)
        val sightData = sightDataResource.toSightData()
        assertEquals(1, sightData.id)
        assertEquals("Resource", sightData.name)
        assertEquals("Desc", sightData.description)
        assertEquals("Long Desc", sightData.longDescription)
        assertEquals("videoUrl", sightData.videoUrl)
        assertEquals("audioUrl", sightData.audioUrl)
        assertEquals(40.7128, sightData.latitude, 0.001)
        assertEquals(-74.0060, sightData.longitude, 0.001)
    }

    @Test
    fun testToSightEntity() {
        val sightPhoto = SightPhoto(1, "Photo", "Photo Description", "photoUrl")
        val sightEntity = sightPhoto.toSightEntity()
        assertEquals(1, sightEntity.id)
        assertEquals("Photo", sightEntity.name)
        assertEquals("Photo Description", sightEntity.description)
        assertEquals("photoUrl", sightEntity.imageUrl)
    }

    @Test
    fun testToSight() {
        val sightEntity = SightEntity(1, "Entity", "Entity Description", "entityUrl")
        val sight = sightEntity.toSight()
        assertEquals(1, sight.id)
        assertEquals("Entity", sight.name)
        assertEquals("Entity Description", sight.description)
        assertEquals("entityUrl", sight.imageUrl)
    }
}
