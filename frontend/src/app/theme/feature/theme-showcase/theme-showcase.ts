// theme-showcase.component.ts
import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ToggleButton, ToggleButtonModule} from "primeng/togglebutton";
import {InputText, InputTextModule} from "primeng/inputtext";
import {Button, ButtonModule} from "primeng/button";
import {RadioButton, RadioButtonModule} from "primeng/radiobutton";
import {Checkbox, CheckboxModule} from "primeng/checkbox";
import {Slider, SliderModule} from "primeng/slider";
import {CardModule} from "primeng/card";
import {ToastModule} from "primeng/toast";
import {Select} from "primeng/select";
import {Tab, TabList, TabPanel, TabPanels, Tabs} from "primeng/tabs";

@Component({
    selector: 'app-theme-showcase',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        ButtonModule,
        CardModule,
        CheckboxModule,
        InputTextModule,
        RadioButtonModule,
        SliderModule,
        ToastModule,
        ToggleButtonModule,
        ToggleButton,
        InputText,
        Button,
        RadioButton,
        Checkbox,
        Slider,
        Select,
        Tabs,
        TabList,
        Tab,
        TabPanels,
        TabPanel
    ],
    templateUrl: './theme-showcase.html'
})
export class ThemeShowcaseComponent {
    checked = true;
    radioValue = 'option1';
    sliderValue = 50;
    darkMode = false;
    hoverCard = false;

    cities = [
        { name: 'New York', code: 'NY' },
        { name: 'London', code: 'LDN' },
        { name: 'Paris', code: 'PRS' },
        { name: 'Tokyo', code: 'TKY' }
    ];

    selectedCity: any;

    get debugInfo() {
        return JSON.stringify({
            theme: this.darkMode ? 'dark' : 'light',
            surfaceClasses: ['.surface-0', '.surface-50', '.surface-100',
                '.surface-card', '.surface-ground', '.content'],
            expectedColors: {
                light: {
                    'surface-0': 'white',
                    'surface-50': 'sand.50',
                    'surface-100': 'sand.100',
                    'surface-card': 'sand.50 (via surface token)',
                    'surface-ground': 'sand.100 (via ground token)',
                    'content-background': 'white'
                },
                dark: {
                    'surface-0': 'coffee.950',
                    'surface-50': 'coffee.900',
                    'surface-100': 'coffee.800',
                    'surface-card': 'coffee.950 (via surface token)',
                    'surface-ground': 'coffee.900 (via ground token)',
                    'content-background': 'coffee.900'
                }
            }
        }, null, 2);
    }

    toggleTheme() {
        if (this.darkMode) {
            document.body.classList.add('dark-mode');
        } else {
            document.body.classList.remove('dark-mode');
        }
    }

}
