import { HttpClient } from '@angular/common/http';
import { Component, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-upload-papers',
  templateUrl: './upload-papers.component.html',
  styleUrls: ['./upload-papers.component.css'],
  encapsulation: ViewEncapsulation.ShadowDom
})
export class UploadPapersComponent {

  uploadForm: FormGroup;
  selectedFile: File | null = null;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.uploadForm = this.fb.group({
      semester: [''],
      year: [''],
      courseName: ['']
    });
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onSubmit() {
    if (!this.selectedFile) {
      alert("Please select a file!");
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);
    formData.append('semester', this.uploadForm.get('semester')?.value);
    formData.append('year', this.uploadForm.get('year')?.value);
    formData.append('courseName', this.uploadForm.get('courseName')?.value);

    this.http.post('http://localhost:8080/api/files/upload', formData)
      .subscribe({
        next: res => console.log('Uploaded successfully:', res),
        error: err => console.error('Upload error:', err)
      });
  }
}
