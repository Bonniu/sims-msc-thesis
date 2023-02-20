import { Component, OnInit } from '@angular/core';
import { Log } from './model/log';
import { HttpClient } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { LogService } from './service/log.service';

@Component({
  selector: 'logs-browser',
  templateUrl: './logs-browser.component.html',
  styleUrls: ['./logs-browser.component.scss'],
})
export class LogsBrowserComponent implements OnInit {
  logs: Log[] = [];
  logsToDisplay: Log[] = [];
  currentSize = 100;
  displayedColumns: string[] = [
    'id',
    'dateTime',
    'message',
    'threadName',
    'logLevel',
    'classPath',
    'username',
  ];

  constructor(
    private http: HttpClient,
    private logService: LogService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.logService.getLogs().subscribe((next) => {
      this.logs = next;
      this.logsToDisplay = this.logs.slice(1, this.currentSize);
    });
  }

  showNextLogs() {
    this.logsToDisplay = this.logs.slice(
      this.currentSize,
      this.currentSize + 100
    );
    this.currentSize += 100;
  }

  goToStart() {
    this.currentSize = 100;
    this.logsToDisplay = this.logs.slice(1, this.currentSize);
  }
}
