import {definePreset, palette} from '@primeuix/themes';
import Aura from '@primeuix/themes/aura';

export const CommonGroundTheme = definePreset(Aura, {
    primitive: {
        sky: palette('#65BCDC'),
        coral: palette('#F06D58'),
        electric: palette('#4845F7'),
        sand: palette('#F3EAE2'),
        coffee: palette('#2B1503')
    },
    semantic: {
        surface: palette('{coffee}'),
        primary: palette('{coral}'),
        secondary: palette('{sky}'),

        colorScheme: {
            light: {
                surface: '{}',
            }
        }
    },
    components: {
        card: {
            root: {
                background: '{surface.50}',
                color: '{surface.950}'
            }
        },
        dialog: {
            root: {
                background: '{sand.50}'
            }
        },
        datepicker: {
            panel: {
                background: '{surface.50}'
            }
        },
        multiselect: {
            overlay: {
                background: '{surface.50}'
            }
        },
        datatable: {
            row: {
                background: '{surface.50}',
                color: '{surface.950}'
            },
            headerCell: {
                background: '{surface.100}',
                color: '{surface.950}'
            }
        },
        drawer: {
            root: {
                background: '{coral.500}',
                color: '{coral.950}',
            }
        },
    }
});
