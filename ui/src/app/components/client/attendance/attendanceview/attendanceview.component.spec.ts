import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceviewComponent } from './attendanceview.component';

describe('AttendanceviewComponent', () => {
  let component: AttendanceviewComponent;
  let fixture: ComponentFixture<AttendanceviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttendanceviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendanceviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
