/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.api.*;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * A class acting a communication tunnel (API) between
 * {@link CropClick} and other plugins.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.0.0
 */
@Getter
public final class CropClickAPI {

    @NotNull
    private final CropAPI cropManager;

    @NotNull
    private final AddonAPI addonManager;

    @NotNull
    private final UpdateAPI updateManager;

    @NotNull
    private final AutofarmAPI autofarmManager;

    @NotNull
    private final FarmWorldAPI farmWorldManager;


    public CropClickAPI() {
        CropClick plugin = CropClick.getInstance();

        // Add cropsconfig somehow???

        this.cropManager = plugin.getCropManager();
        this.addonManager = plugin.getAddonManager();
        this.updateManager = plugin.getUpdateManager();
        this.farmWorldManager = plugin.getWorldManager();
        this.autofarmManager = plugin.getAutofarmManager();
    }

}