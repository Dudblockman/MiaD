package dudblockman.miad.common.item;

import java.text.NumberFormat;
import java.util.List;

import dudblockman.miad.common.energy.ItemEnergyStorage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;


public class ItemCapacitor extends ItemBase {

    protected int energy;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;
    
	public ItemCapacitor(String name) {
		super(name);
		setMaxStackSize(1);
		setMaxDamage(1600);
		setNoRepair();
		
        this.capacity = 1000000;
        this.maxReceive = 1600;
        this.maxExtract = 800;
        this.energy = Math.max(0 , Math.min(capacity, energy));
        
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
	
	@Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
            	return 1.0 - (storage.getEnergyStored() / (double) storage.getMaxEnergyStored());
            }
        }
        return 1.0;
    }
	//@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        if(stack.hasCapability(CapabilityEnergy.ENERGY, null)){
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if(storage != null){
                NumberFormat format = NumberFormat.getInstance();
                list.add(format.format(storage.getEnergyStored())+"/"+format.format(storage.getMaxEnergyStored()));
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt){
        return new EnergyCapabilityProvider(stack, this);
    }

    private static class EnergyCapabilityProvider implements ICapabilityProvider{
    	public final ItemEnergyStorage storage;

		public EnergyCapabilityProvider(final ItemStack stack, ItemCapacitor item){
			this.storage = new ItemEnergyStorage(item.capacity, item.maxReceive, item.maxExtract){
                @Override
                public int getEnergyStored(){
                    if(stack.hasTagCompound()){
                        return stack.getTagCompound().getInteger("Energy");
                    }
                    else{
                        return 0;
                    }
                }

                @Override
                public void setEnergyStored(int energy){
                    if(!stack.hasTagCompound()){
                        stack.setTagCompound(new NBTTagCompound());
                    }

                    stack.getTagCompound().setInteger("Energy", energy);
                }
            };
         }
		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return this.getCapability(capability, facing) != null;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if(capability == CapabilityEnergy.ENERGY){
                return (T)this.storage;
            }
            return null;
		}
    	
    }
}
