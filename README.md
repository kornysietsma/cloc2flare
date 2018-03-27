# cloc2flare

A Clojure library designed to convert cloc's yml format to flare nested json
for use in d3 visualisations

## Usage

`cloc2flare` takes the output of `cloc --by-file --yaml` on standard input, and
produces a JSON Flare file as standard output.

This is easiest if you have an uberjar so you don't need to understand clojure and leiningen -
once I have github releases sorted out, you can download a jar file and run:

`cloc . --by-file --yaml --quiet | java -jar cloc2flare.jar > output.json`

This requires `cloc` which you can generally install from a package manager
on your system, or the source is at https://github.com/AlDanial/cloc

See also https://github.com/kornysietsma/toxic-code-explorer-demo where
there will eventually be documentation on how this all fits together

## License

Copyright © 2018 Kornelis Sietsma

Licensed under the Apache License, Version 2.0 - see LICENSE.txt for details

## TODO

This really needs tests - it's very simple, it should be easy to test!
