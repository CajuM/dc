{ pkgs ? import <nixpkgs> { } }:

pkgs.mkShell {
  name = "dc-l5";
  buildInputs = with pkgs; [
    gradle_6
    jdk11
    (python3.withPackages (ps: with ps; [
      matplotlib
      numpy
    ]))
  ];
}
