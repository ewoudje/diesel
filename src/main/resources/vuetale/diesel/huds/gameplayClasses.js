class e {
  constructor(t, a, i = "rarrow") {
    this.name = t, this.keybind = a, this.iconName = i, this.iconPath = `Img/icon/${i}.png`;
  }
  iconPath;
}
class n {
  constructor(t, a, i, o = "default", r = "bullets") {
    this.name = t, this.maxAmmo = a, this.abilities = i, this.reticleName = o, this.iconName = r, this.previewPath = `Img/item/${t}.png`, this.reticle = `Img/reticle/${o}.png`, this.iconPath = `Img/icon/${r}.png`;
  }
  previewPath;
  reticle;
  iconPath;
}
class s {
  constructor(t, a = "default", i, o, r = "#FB0004") {
    this.name = t, this.voiceName = a, this.maxDashes = i, this.hotbar = o, this.baseColor = r, this.healthPath = `Img/health/${t}.png`;
  }
  healthPath;
}
function c() {
  return {
    scout: new s(
      "scout",
      void 0,
      4,
      [
        new n("esharp", 4, [new e("dash", "Ability1", "larrow"), new e("abscond", "Secondary", "trarrow")]),
        new n("srbnde", 0, [new e("dash", "Ability1", "larrow"), new e("block", "Secondary", "guard")], void 0, "knife")
      ],
      "#00da3a"
    ),
    medic: new s(
      "medic",
      void 0,
      4,
      [
        new n("revolver", 4, [new e("dash", "Ability1"), new e("abscond", "Secondary")]),
        new n("knife", 0, [new e("dash", "Ability1"), new e("block", "Secondary")])
      ]
    ),
    heavy: new s(
      "heavy",
      void 0,
      4,
      [
        new n("shotgun", 4, [new e("dash", "Ability1"), new e("abscond", "Secondary")]),
        new n("knife", 0, [new e("dash", "Ability1"), new e("block", "Secondary")])
      ]
    ),
    jack: new s(
      "jack",
      void 0,
      4,
      [
        new n("shotgun", 4, [new e("dash", "Ability1"), new e("abscond", "Secondary")]),
        new n("knife", 0, [new e("dash", "Ability1"), new e("block", "Secondary")])
      ]
    ),
    turret: new s(
      "turret",
      void 0,
      0,
      [
        new n("antiair", 4, [new e("chaff", "Secondary"), new e("flak", "Ability1")])
      ]
    )
  };
}
export {
  c as getPlayerClasses
};
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZ2FtZXBsYXlDbGFzc2VzLmpzIiwic291cmNlcyI6W10sInNvdXJjZXNDb250ZW50IjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7OyJ9
