FROM pklehre/niso-lab3

ADD ArgParser.java /bin
ADD Main.java /bin
ADD ProbDistribution.java /bin
ADD Strategy.java /bin
ADD RandomWrapper.java /bin
ADD ElFarolBar.java /bin
ADD WeeklyAttendance.java /bin
ADD Individual.java /bin

RUN javac ./bin/*.java -d ./bin/.

CMD ["-username", "bas782", "-submission" , "java -cp ./bin/ Main"]