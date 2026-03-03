// theme-showcase.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {ToggleButton, ToggleButtonModule} from "primeng/togglebutton";
import {InputText, InputTextModule} from "primeng/inputtext";
import {Button, ButtonModule} from "primeng/button";
import {RadioButton, RadioButtonModule} from "primeng/radiobutton";
import {Checkbox, CheckboxModule} from "primeng/checkbox";
import {Slider, SliderModule} from "primeng/slider";
import {CardModule} from "primeng/card";
import {ToastModule} from "primeng/toast";
import {Select} from "primeng/select";
import {Tag} from "primeng/tag";
import {Tab, TabList, TabPanel, TabPanels, Tabs} from "primeng/tabs";

function DropdownModule() {

}

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
    template: `
        <div class="surface-ground p-4 min-h-screen">
            <div class="container mx-auto">

                <!-- Header -->
                <div class="mb-6">
                    <h1 class="text-4xl font-bold mb-2 text-color">Theme Showcase</h1>
                    <p class="text-lg text-color-secondary">Testing your custom PrimeNG theme with Coral, Sky, Electric,
                        and Coffee colors</p>
                </div>

                <div class="grid grid-cols-1 lg:grid-cols-5 gap-4 mb-8">
                    <div class="p-4 rounded-lg">
                        <h3 class="font-bold primary">Coral</h3>
                        <p class="text-sm">Primary Color</p>
                        <p class="text-sm">#F06D58</p>
                    </div>
                </div>

                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">

                    <!-- Left Column -->
                    <div>
                        <!-- Buttons -->
                        <div class="card mb-6">
                            <h2 class="text-2xl font-bold mb-4 text-color">Buttons</h2>
                            <div class="flex flex-wrap gap-3 mb-4">
                                <p-button label="Primary"></p-button>
                                <p-button label="Secondary" severity="secondary"></p-button>
                                <p-button label="Success" severity="success"></p-button>
                                <p-button label="Info" severity="info"></p-button>
                                <p-button label="Warning" severity="warn"></p-button>
                                <p-button label="Help" severity="help"></p-button>
                                <p-button label="Danger" severity="danger"></p-button>
                            </div>
                            <div class="flex flex-wrap gap-3">
                                <p-button label="Outlined" variant="outlined" severity="primary"></p-button>
                                <p-button label="Secondary" variant="outlined" severity="secondary"></p-button>
                                <p-button label="Text Only" variant="text" severity="primary"></p-button>
                                <p-button label="Secondary Text" variant="text" severity="secondary"></p-button>
                            </div>
                        </div>

                        <!-- Form Controls -->
                        <div class="card mb-6">
                            <h2 class="text-2xl font-bold mb-4 text-color">Form Controls</h2>
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                <!-- Input Fields -->
                                <div>
                                    <label for="username" class="block mb-2 text-color">Username</label>
                                    <input pInputText id="username" class="w-full mb-4" placeholder="Enter username"/>

                                    <label for="password" class="block mb-2 text-color">Password</label>
                                    <input pInputText id="password" type="password" class="w-full"
                                           placeholder="Enter password"/>
                                </div>

                                <!-- Checkboxes & Radio -->
                                <div>
                                    <div class="field-checkbox mb-3">
                                        <p-checkbox [(ngModel)]="checked" [binary]="true" inputId="check"></p-checkbox>
                                        <label for="check" class="ml-2 text-color">Remember me</label>
                                    </div>

                                    <div class="field-radiobutton mb-3">
                                        <p-radioButton [(ngModel)]="radioValue" value="option1"
                                                       inputId="option1"></p-radioButton>
                                        <label for="option1" class="ml-2 text-color">Option 1</label>
                                    </div>

                                    <div class="field-radiobutton">
                                        <p-radioButton [(ngModel)]="radioValue" value="option2"
                                                       inputId="option2"></p-radioButton>
                                        <label for="option2" class="ml-2 text-color">Option 2</label>
                                    </div>
                                </div>
                            </div>

                            <!-- Dropdown -->
                            <div class="mt-4">
                                <label for="city" class="block mb-2 text-color">Select City</label>
                                <p-select [options]="cities" [(ngModel)]="selectedCity" placeholder="Select a City"
                                          optionLabel="name" class="w-full"></p-select>
                            </div>

                            <!-- Slider -->
                            <div class="mt-6">
                                <label class="block mb-2 text-color">Slider: {{ sliderValue }}</label>
                                <p-slider [(ngModel)]="sliderValue" [min]="0" [max]="100" class="w-full"></p-slider>
                            </div>
                        </div>
                    </div>

                    <!-- Right Column -->
                    <div>
                        <div class="p-6 min-h-screen">

                            <h1 class="text-primary mb-4">Theme Validation</h1>

                            <div class="grid gap-y-4">
                                <div class="col-12">
                                    <p-card header="Standard Surface Card" subheader="Mapped to Surface 0">
                                        <p>
                                            If this card is light and the text is dark coffee,
                                            your <b>surface.0</b> and <b>root.color</b> mapping is successful.
                                        </p>
                                        <ng-template pTemplate="footer">
                                            <div class="flex gap-4 mt-1">
                                                <p-button label="Primary Button" icon="pi pi-check"></p-button>
                                                <p-button label="Secondary" severity="secondary"
                                                          variant="outlined"></p-button>
                                            </div>
                                        </ng-template>
                                    </p-card>
                                </div>
                                <div class="col-12">
                                    <p-card class="overflow-hidden">
                                        <ng-template #header>
                                            <img alt="Card" class="w-full"
                                                 src="https://primefaces.org/cdn/primeng/images/card-ng.jpg"/>
                                        </ng-template>
                                        <ng-template #title> Advanced Card</ng-template>
                                        <ng-template #subtitle> Card subtitle</ng-template>
                                        <p>
                                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Inventore sed
                                            consequuntur error repudiandae numquam deserunt quisquam repellat libero
                                            asperiores earum nam nobis, culpa ratione quam perferendis esse, cupiditate
                                            neque
                                            quas!
                                        </p>
                                        <ng-template #footer>
                                            <div class="flex gap-4 mt-1">
                                                <p-button label="Cancel" severity="secondary" class="w-full"
                                                          [outlined]="true" styleClass="w-full"/>
                                                <p-button label="Save" class="w-full" styleClass="w-full"/>
                                            </div>
                                        </ng-template>
                                    </p-card>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Tabs -->
                <div class="card mt-6">
                    <h2 class="text-2xl font-bold mb-4 text-color">Tabs</h2>
                    <div class="card">
                        <p-tabs value="0">
                            <p-tablist>
                                <p-tab value="0">Header I</p-tab>
                                <p-tab value="1">Header II</p-tab>
                                <p-tab value="2">Header III</p-tab>
                            </p-tablist>
                            <p-tabpanels>
                                <p-tabpanel value="0">
                                    <p class="m-0">
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                                        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est
                                        laborum.
                                    </p>
                                </p-tabpanel>
                                <p-tabpanel value="1">
                                    <p class="m-0">
                                        Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo
                                        enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Consectetur, adipisci velit, sed quia non numquam eius modi.
                                    </p>
                                </p-tabpanel>
                                <p-tabpanel value="2">
                                    <p class="m-0">
                                        At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in
                                        culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus.
                                    </p>
                                </p-tabpanel>
                            </p-tabpanels>
                        </p-tabs>
                    </div>
                </div>

                <!-- Messages -->
                <div class="card mt-6">
                    <h2 class="text-2xl font-bold mb-4 text-color">Messages</h2>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div class="surface-card p-4 border-round border border-primary-200">
                            <div class="flex items-center">
                                <i class="pi pi-info-circle text-primary-500 mr-3 text-xl"></i>
                                <div>
                                    <h4 class="font-bold text-color">Info Message</h4>
                                    <p class="text-sm text-color-secondary mt-1">This is an informational message using
                                        primary colors.</p>
                                </div>
                            </div>
                        </div>

                        <div class="surface-card p-4 border-round border border-surface-200">
                            <div class="flex items-center">
                                <i class="pi pi-check-circle text-green-500 mr-3 text-xl"></i>
                                <div>
                                    <h4 class="font-bold text-color">Success Message</h4>
                                    <p class="text-sm text-color-secondary mt-1">Operation completed successfully.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Theme Toggle -->
                <div class="flex justify-center mt-8">
                    <div class="flex items-center gap-4 p-4 border-round surface-card">
                        <span class="text-color">Theme Mode:</span>
                        <p-toggleButton [(ngModel)]="darkMode"
                                        onLabel="Dark"
                                        offLabel="Light"
                                        onIcon="pi pi-moon"
                                        offIcon="pi pi-sun"
                                        (onChange)="toggleTheme()"></p-toggleButton>
                    </div>
                </div>

                <div class="mt-8 p-4 border-round surface-card">
                    <h3 class="text-xl font-bold mb-2 text-color">Debug Info</h3>
                    <pre class="text-sm overflow-auto p-2 surface-100 border-round">{{ debugInfo }}</pre>
                </div>


            </div>
        </div>

    `
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

    toggleTheme() {
        if (this.darkMode) {
            document.body.classList.add('dark-mode');
        } else {
            document.body.classList.remove('dark-mode');
        }
    }

    get debugInfo() {
        return JSON.stringify({
            theme: this.darkMode ? 'dark' : 'light',
            surfaceClasses: ['.surface-0', '.surface-50', '.surface-100', '.surface-card', '.surface-ground', '.content'],
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

}
