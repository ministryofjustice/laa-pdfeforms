import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceindexComponent } from './attendanceindex.component';

describe('AttendanceindexComponent', () => {
  let component: AttendanceindexComponent;
  let fixture: ComponentFixture<AttendanceindexComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttendanceindexComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendanceindexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
