import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendancenoteviewComponent } from './attendancenoteview.component';

describe('AttendancenoteviewComponent', () => {
  let component: AttendancenoteviewComponent;
  let fixture: ComponentFixture<AttendancenoteviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttendancenoteviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendancenoteviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
