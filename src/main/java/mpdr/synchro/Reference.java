package mpdr.synchro;


public class Reference {
    public enum ModBlocks{
        SYNCHRO_CHEST("synchroChest", "synchro_chest");

        private String unlocalizedName;
        private String registryName;

        ModBlocks(String unlocalizedName, String registryName){
            this.unlocalizedName = unlocalizedName;
            this.registryName = registryName;
        }

        public String getUnlocalizedName() {
            return unlocalizedName;
        }

        public String getRegistryName() {
            return registryName;
        }

    }
}
