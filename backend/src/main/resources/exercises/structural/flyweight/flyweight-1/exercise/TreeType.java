// Provided — do not edit
// TreeType stores the intrinsic state (shared across many trees): species, color, and texture
class TreeType {
    private final String species;
    private final String color;
    private final String texture;

    TreeType(String species, String color, String texture) {
        this.species = species;
        this.color   = color;
        this.texture = texture;
    }

    public String getSpecies() { return species; }
    public String getColor() { return color; }
    public String getTexture() { return texture; }
}
