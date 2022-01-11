{ pkgs ? import <nixpkgs> { } }:

pkgs.mkShell {
  name = "dc-l4";
  buildInputs = with pkgs; [
    gradle_6
    hadoop3
    jdk11
    (python3.withPackages (ps: with ps; [
      matplotlib
      numpy
      scipy
    ]))
  ];
}
