package com.github.bakuplayz.cropclick.menus.addons.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractAddonMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public final class AddonMenuStateBuilder {

    @NotNull
    public static AddonMenuStateHandler createStateHandler(@NotNull AbstractAddonMenu menu, @NotNull CropClick plugin, @NotNull Addon addon) {
        return new AddonMenuStateHandler(menu, plugin, addon);
    }


    public static class AddonMenuStateHandler extends MenuStateHandler<AddonMenuState, AbstractAddonMenu> {

        private final Addon addon;

        private final AddonsConfig addonsConfig;


        private AddonMenuStateHandler(@NotNull AbstractAddonMenu observer, @NotNull CropClick plugin, @NotNull Addon addon) {
            super(observer, new AddonMenuState(addon));
            this.addon = addon;
            this.addonsConfig = plugin.getAddonsConfig();
        }


        public void toggleAddon() {
            updateState(state.isAddonEnabled, (state) -> !state, AddonMenuStateFlag.ADDON_STATE);
        }


        @Override
        protected <P> AddonMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == AddonMenuStateFlag.ADDON_STATE) {
                state.setAddonEnabled(infer(partial));
                addonsConfig.setAddonState(addon.getName(), infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class AddonMenuState implements MenuState {

        private boolean isAddonEnabled;


        private AddonMenuState(@NotNull Addon addon) {
            this.isAddonEnabled = addon.isEnabled();
        }

    }

    public static final class AddonMenuStateFlag {

        public final static int ADDON_STATE = 0x1;

    }

}
