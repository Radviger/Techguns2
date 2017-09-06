package techguns;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import techguns.init.ITGInitializer;

public class TGFluids implements ITGInitializer {
	
	/**
	 * Same as FluidRegistry.WATER
	 */
	public static Fluid WATER;
	/**
	 * Same as FluidRegistry.LAVA
	 */
	public static Fluid LAVA;
	
	public static Fluid MILK;
	
	public static Fluid OIL;
	
	public static Fluid FUEL;
	
	public static Fluid LIQUID_REDSTONE;
	
	public static Fluid LIQUID_COAL;

	public static Fluid ACID;
	
	public static Fluid LIQUID_ENDER;
	
	public static Fluid BIOFUEL;

	public static boolean addedMilk=false;

	public static ArrayList<IFluidBlock> FLUIDBLOCKS = new ArrayList<>();
	
	public static Block BLOCK_FLUID_ACID;
	
	public static boolean addedAcid=false;
	
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		WATER=FluidRegistry.WATER;
		LAVA=FluidRegistry.LAVA;
		
		addedAcid = FluidRegistry.registerFluid(new Fluid("creeper_acid", new ResourceLocation(Techguns.MODID, "blocks/acid_still"), new ResourceLocation(Techguns.MODID, "blocks/acid_flow")).setGaseous(false).setLuminosity(0).setUnlocalizedName("creeperAcid").setDensity(100));
		ACID = FluidRegistry.getFluid("creeper_acid");
		if(addedAcid) {
			
			BLOCK_FLUID_ACID = new BlockFluidClassic(ACID,Material.WATER).setRegistryName(new ResourceLocation(Techguns.MODID, "block_creeper_acid"))
					.setUnlocalizedName(Techguns.MODID+".block_creeper_acid").setCreativeTab(Techguns.tabTechgun);
		}
	}

	public void registerItems(RegistryEvent.Register<Item> event) {
		if(BLOCK_FLUID_ACID!=null) {
			ItemBlock ib = new ItemBlock(BLOCK_FLUID_ACID);
			ib.setRegistryName(BLOCK_FLUID_ACID.getRegistryName());
			event.getRegistry().register(ib);
		}
	}
	
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		if(BLOCK_FLUID_ACID!=null) {
			event.getRegistry().register(BLOCK_FLUID_ACID);
			FluidRegistry.addBucketForFluid(ACID);
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels() {
		if(BLOCK_FLUID_ACID!=null) {
			registerFluidModelsForFluidBlock(BLOCK_FLUID_ACID);
		}
	}
	
	protected static void registerFluidModelsForFluidBlock(Block b) {
		if(!(b instanceof IFluidBlock)) {
			System.out.println("Tried to register "+b+ " as Fluid, but block is no IFluidBlock");
			return;
		}
		IFluidBlock f = (IFluidBlock) b;
		
		final Item item = Item.getItemFromBlock(b);
		if(item==Items.AIR) {
			System.out.println("No item found for IFluidBlock "+b);
			return;
		}
		
		ModelBakery.registerItemVariants(item);
		
		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(new ResourceLocation(Techguns.MODID, "fluid"), f.getFluid().getName());

		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return modelResourceLocation;
			}
		});

		ModelLoader.setCustomStateMapper(b, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(final IBlockState bs) {
				return modelResourceLocation;
			}
		});
	}

	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		MILK = FluidRegistry.getFluid("milk");
		
		/*	if (MILK==null){
				MILK = new Fluid("milk").setGaseous(false).setDensity(1050).setViscosity(1050).setUnlocalizedName("milk");
				addedMilk=true;
				FluidRegistry.registerFluid(MILK);
				FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("milk", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(Items.milk_bucket), new ItemStack(Items.bucket));
			}	*/
			
			OIL = FluidRegistry.getFluid("oil");
			FUEL = FluidRegistry.getFluid("fuel");

			LIQUID_REDSTONE = FluidRegistry.getFluid("redstone");
			if (LIQUID_REDSTONE==null){
		//		System.out.println("No Liquid Redstone, fallback to lava");
				LIQUID_REDSTONE = LAVA;
			}
			
			LIQUID_COAL = FluidRegistry.getFluid("coal");
			if (LIQUID_COAL==null){
		//		System.out.println("No Liquid coal, fallback to oil/water");
				
				if ( OIL!=null){
					LIQUID_COAL = OIL;
				} else {
				
					LIQUID_COAL = WATER;
				}
			}
			
			LIQUID_ENDER = FluidRegistry.getFluid("ender");
			if (LIQUID_ENDER==null){
//				System.out.println("No Liquid ender, fallback to lava");
				LIQUID_ENDER = LAVA;
			}
			
			BIOFUEL = FluidRegistry.getFluid("biofuel");
			if (BIOFUEL==null){
		//		System.out.println("No Biofuel, fallback to lava");
				BIOFUEL = LAVA;
			}
	}
}