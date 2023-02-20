import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Log } from '../model/log';
import { Observable } from 'rxjs';

@Injectable()
export class LogService {
  private configUrl = 'http://localhost:8080/logs';

  constructor(private http: HttpClient) {}

  getLogs(): Observable<Log[]> {
    let observable = this.http.get<Log[]>(this.configUrl);
    console.log(observable)
    return observable;
  }

}
