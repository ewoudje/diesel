declare class abilityEntry {
    name: string;
    keybind: string;
    iconName: string;
    iconPath: string;

    constructor(name: string, keybind: string, iconName?: string);
}

declare class hotbarEntry {
    name: string;
    maxAmmo: number;
    abilities: abilityEntry[];
    reticleName: string;
    iconName: string;
    previewPath: null | string;
    reticle: string;
    iconPath: string;

    constructor(name: string, maxAmmo: number, abilities: abilityEntry[], reticleName?: string, iconName?: string);
}

declare class playerClass {
    name: string;
    voiceName: string;
    maxDashes: number;
    hotbar: hotbarEntry[];
    baseColor: string;
    healthPath: string;

    constructor(name: string, //also generates healthbar path
                voiceName: string | undefined, maxDashes: number, hotbar: hotbarEntry[], //aray of hotbar entries
                baseColor?: string);
}

declare function getPlayerClasses(): {
    scout: playerClass;
    medic: playerClass;
    heavy: playerClass;
    jack: playerClass;
    turret: playerClass;
};

export {getPlayerClasses};
