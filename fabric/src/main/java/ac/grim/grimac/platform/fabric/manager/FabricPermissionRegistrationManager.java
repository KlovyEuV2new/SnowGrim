package ac.grim.grimac.platform.fabric.manager;

import ac.grim.grimac.platform.api.manager.PermissionRegistrationManager;
import ac.grim.grimac.platform.api.permissions.PermissionDefaultValue;
import ac.grim.grimac.platform.fabric.GrimACFabricLoaderPlugin;
import ac.grim.grimac.platform.fabric.sender.FabricSenderFactory;
import me.lucko.fabric.api.permissions.v0.Permissions;

public class FabricPermissionRegistrationManager implements PermissionRegistrationManager {

    private final FabricSenderFactory fabricSenderFactory = GrimACFabricLoaderPlugin.LOADER.getFabricSenderFactory();

    public FabricPermissionRegistrationManager() {
        registerPermission("snowgrim.exempt", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.nosetback", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.nomodifypacket", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.nosetback", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.alerts.enable-on-join", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.verbose.enable-on-join", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.brand.enable-on-join", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.alerts.enable-on-join.silent", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.verbose.enable-on-join.silent", PermissionDefaultValue.FALSE);
        registerPermission("snowgrim.brand.enable-on-join.silent", PermissionDefaultValue.FALSE);
    }

    @Override
    public void registerPermission(String name, PermissionDefaultValue defaultValue) {
        fabricSenderFactory.registerPermissionDefault(name, defaultValue);
        Permissions.check(GrimACFabricLoaderPlugin.FABRIC_SERVER.createCommandSourceStack(), name);
    }
}
