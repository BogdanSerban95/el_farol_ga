FROM pklehre/niso-lab3

ADD ArgParser.java /bin
ADD Main.java /bin
ADD ProbDistribution.java /bin
ADD Strategy.java /bin
ADD RandomWrapper.java /bin

RUN javac ./bin/*.java -d ./bin/.

CMD ["-username", "bas782", "-submission" , "java -cp ./bin/ Main"]