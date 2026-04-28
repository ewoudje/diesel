//data for each weapon
//abilities r kinda stupid but time crunch
class abilityEntry{
    iconPath: string
    constructor(
        public name: string,
        public keybind: string,
        public iconName: string = 'rarrow'
    ){
        this.iconPath = `Img/icon/${iconName}.png`
    }
}

class hotbarEntry{
    previewPath: null | string;
    reticle: string;
    iconPath: string;

    constructor(
        public name: string,
        public maxAmmo: number,
        public abilities: abilityEntry[],
        public reticleName: string = 'default',
        public iconName: string = 'bullets'
    ){
        this.previewPath = `Img/item/${name}.png`
        this.reticle = `Img/reticle/${reticleName}.png`
        this.iconPath = `Img/icon/${iconName}.png`
    }
}
//data for each class
class playerClass{
    healthPath: string
    constructor(
        public name: string, //also generates healthbar path
        public voiceName: string = 'default',
        public maxDashes : number,
        public hotbar: hotbarEntry[], //aray of hotbar entries
        public baseColor: string = '#FB0004'
    ){  
        this.healthPath = `Img/health/${name}.png`
    }
}

function getPlayerClasses(){
    return {
        scout: new playerClass('scout',
            undefined,
            4,[
            new hotbarEntry('esharp',4,[new abilityEntry('dash','Ability1','larrow'),new abilityEntry('abscond','Secondary','trarrow')]),
            new hotbarEntry('srbnde',0,[new abilityEntry('dash','Ability1','larrow'),new abilityEntry('block','Secondary','guard')],undefined,'knife'),
        ],'#00da3a'),

        medic: new playerClass('medic',
            undefined,
            4,[
            new hotbarEntry('revolver',4,[new abilityEntry('dash','Ability1'),new abilityEntry('abscond','Secondary')]),
            new hotbarEntry('knife',0,[new abilityEntry('dash','Ability1'),new abilityEntry('block','Secondary')]),
        ]),

        heavy: new playerClass('heavy',
            undefined,
            4,[
            new hotbarEntry('shotgun',4,[new abilityEntry('dash','Ability1'),new abilityEntry('abscond','Secondary')]),
            new hotbarEntry('knife',0,[new abilityEntry('dash','Ability1'),new abilityEntry('block','Secondary')]),
        ]),

        jack: new playerClass('jack',
            undefined,
            4,[
            new hotbarEntry('shotgun',4,[new abilityEntry('dash','Ability1'),new abilityEntry('abscond','Secondary')]),
            new hotbarEntry('knife',0,[new abilityEntry('dash','Ability1'),new abilityEntry('block','Secondary')]),
        ]),

        turret: new playerClass('turret',
            undefined,
            0,[
            new hotbarEntry('jczlk',120,[new abilityEntry('chaff','Secondary'),new abilityEntry('flak','Ability1')]),
        ])
    }
}


export { getPlayerClasses }