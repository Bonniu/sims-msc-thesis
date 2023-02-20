import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { HttpClientModule } from '@angular/common/http';
import { NotificationService } from './notifications/service/notification.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormsModule } from '@angular/forms';
import { MatSortModule } from '@angular/material/sort';
import { MessageModalComponent } from './notifications/message-modal/message-modal.component';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { EmailConfigComponent } from './email-config/email-config.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatListModule} from "@angular/material/list";
import {NotificationRecipientService} from "./email-config/service/notification-recipient.service";
import {ScrollingModule} from "@angular/cdk/scrolling";
import {MatIconModule} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    NotificationsComponent,
    MessageModalComponent,
    EmailConfigComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatCheckboxModule,
    MatButtonModule,
    FormsModule,
    MatSortModule,
    MatDialogModule,
    MatGridListModule,
    MatListModule,
    ScrollingModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  providers: [
    {
      provide: MatDialogRef,
      useValue: {},
    },
    NotificationService,
    NotificationRecipientService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
