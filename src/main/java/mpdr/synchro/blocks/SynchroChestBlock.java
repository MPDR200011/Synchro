package mpdr.synchro.blocks;


import mpdr.synchro.Main;
import mpdr.synchro.Reference;
import mpdr.synchro.network.SynchroGUIHandler;
import mpdr.synchro.tileentities.TileEntitySynchroChest;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import javax.annotation.Nullable;

public class SynchroChestBlock extends Block implements ITileEntityProvider{

    public static final int GUI_ID = 1;

    public SynchroChestBlock(Material materialIn) {
        super(materialIn);
        this.setUnlocalizedName(Reference.ModBlocks.SYNCHRO_CHEST.getUnlocalizedName());
        this.setRegistryName(Reference.ModBlocks.SYNCHRO_CHEST.getRegistryName());
        this.setSoundType(SoundType.WOOD);
        this.setHarvestLevel("axe", 0);
        this.setHardness(2.5F);
        this.setResistance(12.5F);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.isBlockContainer = true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySynchroChest();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            ((TileEntitySynchroChest) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (heldItem != null) {
			if (heldItem.getItem() == Items.STICK) {
				TileEntitySynchroChest tile = (TileEntitySynchroChest) worldIn.getTileEntity(pos);
				tile.setFrequency("1");
				return true;
			}
			if (heldItem.getItem() == Items.ROTTEN_FLESH) {
				TileEntitySynchroChest tile = (TileEntitySynchroChest) worldIn.getTileEntity(pos);
				tile.setFrequency("2");
				return true;
			}
		}
    	
    	if (!worldIn.isRemote){
            playerIn.openGui(Main.instance, SynchroGUIHandler.TILE_ENTITY_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
