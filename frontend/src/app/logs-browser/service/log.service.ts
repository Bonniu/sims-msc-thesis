import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Log } from '../model/log';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable()
export class LogService {
  private configUrl = environment.apiUrl + '/logs';

  constructor(private http: HttpClient) {}

  getLogs(): Observable<Log[]> {
    return this.http.get<Log[]>(this.configUrl);
  }
}
