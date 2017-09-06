package techguns;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import techguns.api.capabilities.ITGExtendedPlayer;
import techguns.api.capabilities.ITGShooterValues;
import techguns.capabilities.TGExtendedPlayer;
import techguns.capabilities.TGExtendedPlayerStorage;
import techguns.capabilities.TGShooterValues;
import techguns.capabilities.TGShooterValuesStorage;
import techguns.client.audio.TGSoundCategory;
import techguns.events.TechgunsGuiHandler;
import techguns.init.ITGInitializer;
import techguns.items.guns.GenericGun;

@Mod.EventBusSubscriber
public class CommonProxy implements ITGInitializer {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		this.registerEntityRenderers();
		this.registerCapabilities();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		this.registerItemRenderers();
		NetworkRegistry.INSTANCE.registerGuiHandler(Techguns.MODID, new TechgunsGuiHandler());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		Techguns.blocks.registerBlocks(event);
		Techguns.fluids.registerBlocks(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		Techguns.blocks.registerItems(event);
		Techguns.fluids.registerItems(event);
		Techguns.items.registerItems(event);
		Techguns.armors.registerItems(event);
		Techguns.guns.registerItems(event);
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		TGSounds.registerSounds(event);
	}

	protected void registerItemRenderers(){};
	
	protected void registerEntityRenderers(){};
	
	public void registerCapabilities(){
		CapabilityManager.INSTANCE.register(ITGExtendedPlayer.class, new TGExtendedPlayerStorage(), () -> new TGExtendedPlayer(null));
		CapabilityManager.INSTANCE.register(ITGShooterValues.class, new TGShooterValuesStorage(), TGShooterValues::new);
	}

	/**
	 * Return player on client, null on server
	 * @return
	 */
	public EntityPlayer getPlayerClient() {
		return null;
	}
	
	public void setGunTextures(GenericGun gun, String path, int variations){
		
	}
	
	public void handleSoundEvent(EntityPlayer ply, int entityId, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, boolean playOnOwnPlayer, TGSoundCategory soundCategory){
    	
    }
    public void playSoundOnEntity(Entity ent, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean gunPosition, TGSoundCategory category){
    }
    
    public void playSoundOnPosition(SoundEvent soundname, float posx, float posy, float posz, float volume, float pitch, boolean repeat, TGSoundCategory soundCategory) {
	}

	public void createFX(String name, World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		
	}

	public void createFXOnEntity(String name, Entity ent) {

	}
	
	public void createLightPulse(double x, double y, double z, int lifetime, float rad_start, float rad_end, float r, float g, float b) {};
	
	public void createLightPulse(double x, double y, double z, int fadeIn, int fadeOut, float rad_large, float rad_small, float r, float g, float b) {};
	
    public void setHasStepassist(boolean value){};
    
    public void setHasNightvision(boolean value){};
    
    public void setFlySpeed(float value){};
    
    public boolean getHasStepassist(){
    	return false;
    };
    
    public boolean getHasNightvision(){
    	return false;
    };
    
    public boolean isClientPlayerAndIn1stPerson(EntityLivingBase ent) {
		return false;
	}
    
    public boolean clientInRangeSquared(double posX, double posZ, double distSq) {
		return false;
	}
}