
var Fork = function() {
    this.state = 0;
    return this;
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

Fork.prototype.acquire = async function(cb) { 
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
    sleep_time = 1;
    while(this.state != 0){
        console.log("fork " + cb + " is taken, sleeping...");
        await sleep(sleep_time);
        sleep_time *= 2;
    }
    if(this.state == 0){
        this.state = 1;
    }

}

Fork.prototype.release = function() { 
    this.state = 0; 
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

async function startNaiveAsync(p){
    
}

Philosopher.prototype.startNaive = async function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow

    for (let i = 0; i < count; i++) {
        console.log("Philosopher " + id + " is acquiring forks");
        await forks[f1].acquire(f1);
        await forks[f2].acquire(f2);
        await sleep(30);
        console.log("Philosopher " + id + " is releasing forks");
        await forks[f1].release();
        await forks[f2].release();
        console.log("Philosopher " + id + " is thinking");
        await sleep(50);
    }
}

Philosopher.prototype.startAsym = async function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

        for (let i = 0; i < count; i++) {
            console.log("Philosopher " + id + " is acquiring forks");
            if(this.id %2 == 1){
                await forks[f1].acquire(f1);
                await forks[f2].acquire(f2);
            }else{
                await forks[f2].acquire(f2);
                await forks[f1].acquire(f1);
            }
            await sleep(30);
            console.log("Philosopher " + id + " is releasing forks");
            await forks[f1].release();
            await forks[f2].release();
            console.log("Philosopher " + id + " is thinking");
            await sleep(50);
        }    
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

Philosopher.prototype.startConductor = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}


var N = 5;
var forks = [];
var philosophers = []
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    //philosophers[i].startNaive(10);
    philosophers[i].startAsym(10);
}