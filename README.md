# AVIBÉRICO

Proyecto para el avistamiento de aves en la Península Ibérica.

> ¡Lleva un registro de cada ave que avistas!

> Crea tu perfil de pajarero

> Escoge tu ave favorita en el perfil

> Aprende sobre las aves de la Península Ibérica

Creado por:
* M. del Olmo
* La Grajilla, una grajilla occidental


## Estructura del proyecto

### Bird (Ave)
- Long id
- String commonName (nombre común)
- String species (especie)
- String genus (género)
- String family (familia)
- String order (orden)
- String description (descripción)
- ConservationStatus conservationStatus [enum] (estado de conservación)
- Image image [@OneToOne] (imagen)
- Set<Habitat> habitats [@ManyToMany) (hábitats)

### BirdSighting (Avistamiento de ave)
- Long id
- Bird bird [@ManyToOne] (ave)
- Pajarero pajarero [@ManyToOne] (user)
- LocalDateTime dateTime (fecha y hora)
- String location (ubicación)

### Habitat (Hábitat)
- Long id
- String name (nombre)
- String description (descripción)

### Pajarero (user)
- Long id
- String email (email)
- String password (contraseña)
- String name (nombre)
- Bird favoriteBird [@ManyToOne] (ave favorita)
- Boolean isActive (está activo)
- UserRole role [enum] (rol)
- List<BirdSighting> birdSightings [@OneToMany] (avitamientos de ave)

### Image (Imagen)
- Long id
- String imgUrl (url de imagen)
- String altText (texto alternativo)
- String photographer (fotógrafo)

### ConservationStatus [enum] (Estado de conservación)
- EXTINCT (Extinto)
- EXTINCT_IN_THE_WILD (Extinto en estado silvestre)
- CRITICALLY_ENDANGERED (En peligro crítico)
- ENDANGERED (En peligro)
- VULNERABLE (Vulnerable)
- NEAR_THREATENED (Casi amenazado)
- CONSERVATION_DEPENDENT (Dependiente de conservación)
- LEAST_CONCERN (Preocupación menor)

### UserRole [enum] (Rol de usuario)
- ADMIN (administrador)
- USER (user)


## Presentación en Google Slides
- Modo vista: https://docs.google.com/presentation/d/18jPYYJ7bFsWOB0NWUe4bx97zo3KSAOvkzYbsRK7wXUk/edit?usp=sharing
- Modo edición: https://docs.google.com/presentation/d/1LjQecT6RkbyaqytGOPc1oFjkP8qv1D4Bfy7sWG52g-g/edit?usp=sharing
