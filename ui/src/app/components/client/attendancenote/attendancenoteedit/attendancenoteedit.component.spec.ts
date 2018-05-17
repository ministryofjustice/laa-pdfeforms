import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendancenoteeditComponent } from './attendancenoteedit.component';

describe('AttendancenoteeditComponent', () => {
  let component: AttendancenoteeditComponent;
  let fixture: ComponentFixture<AttendancenoteeditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttendancenoteeditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendancenoteeditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
