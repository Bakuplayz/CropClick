package com.github.bakuplayz.cropclick.yaml;

import com.github.bakuplayz.cropclick.utils.Enableable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class SoundYaml implements Yamlable, Enableable {

    private @Setter @Getter @Accessors(chain = true) double delay;
    private @Setter @Getter @Accessors(chain = true) double pitch;
    private @Setter @Getter @Accessors(chain = true) double volume;


    public SoundYaml(double delay, double pitch, double volume) {
        this.delay = delay;
        this.pitch = pitch;
        this.volume = volume;
    }


    @Override
    @Contract(" -> new")
    public @NotNull Map<String, Object> toYaml() {
        return new HashMap<String, Object>() {{
            put("delay", delay);
            put("pitch", pitch);
            put("volume", volume);
        }};
    }


    @Override
    public boolean isEnabled() {
        return volume != 0.0 & pitch != 0.0;
    }

}